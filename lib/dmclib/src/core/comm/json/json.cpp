//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2014-Dec-24
//----------------------------------------------------------------------------------------------------------------------
// json object
#include <cassert>
#include <core/platfrom/file/file.h>
#include "json.h"
#include <utility>
#include <sstream>
#include <fstream>

using namespace std;

namespace dmc {

	namespace {
		const char separators[] = " \t\r\n";
	}

	//------------------------------------------------------------------------------------------------------------------
	Json::Json(const string& _code) {
		initWithCode(_code);
	}

	//------------------------------------------------------------------------------------------------------------------
	Json::Json(const Json& _src) {
		mType = _src.mType;
		switch (mType)
		{
		case dmc::Json::DataType::integer:
			mInt = _src.mInt;
			break;
		case dmc::Json::DataType::real:
			mFloat = _src.mFloat;
			break;
		case dmc::Json::DataType::text:
			mString = _src.mString;
			break;
		case dmc::Json::DataType::dictionary:
			mDictionary = _src.mDictionary;
			for(auto& i : mDictionary)
				i.second = new Json(*i.second); // Deep-copy
			break;
		case dmc::Json::DataType::list:
			mList = _src.mList;
			for(unsigned i = 0; i < mList.size(); ++i)
				mList[i] = new Json(*mList[i]); // Deep-copy
			break;
		case dmc::Json::DataType::boolean:
			mInt = _src.mInt;
			break;
		default:
			break;
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	Json& Json::operator=(const Json& _src) {
		this->~Json();
		new(this)Json(_src);
		return *this;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json::~Json() {
		if(mType == DataType::list)
			for(auto i : mList)
				delete i;
		else if(mType == DataType::dictionary)
			for(auto i : mDictionary)
				delete i.second;
	}

	//------------------------------------------------------------------------------------------------------------------
	unsigned Json::initWithCode(const string& _code) {
		if(_code.empty()) {
			mType = DataType::nill;
			return 0;
		}
		unsigned cursor = _code.find_first_not_of(separators);
		assert(string::npos != cursor); // Code must be valid

		if(_code.substr(0,5) == "False" || _code.substr(0,5) == "false") {
			mType = DataType::boolean;
			mInt = 0;
			return 5;
		} else if (_code.substr(0,4) == "True" || _code.substr(0,4) == "true") {
			mType = DataType::boolean;
			mInt = 1;
			return 4;
		}else if (_code.substr(0,4) == "Null" || _code.substr(0,4) == "null") {
			mType = DataType::nill;
			mInt = 0;
			return 4;
		} else if('\"' == _code[cursor]) { // Text literal
			unsigned terminator = _code.find('\"', cursor+1);
			unsigned len = terminator - cursor - 1;
			mString = _code.substr(cursor+1,len);
			mType = DataType::text;
			return terminator+1; // Content + 2x"
		} else if('[' == _code[cursor]) {
			mType = DataType::list;
			cursor = _code.find_first_not_of(separators, cursor+1);
			while(_code[cursor] != ']') {
				mList.push_back(new Json());
				Json& element = *mList.back();
				cursor += element.initWithCode(_code.substr(cursor));
				cursor = _code.find_first_not_of(separators, cursor);
				if(_code[cursor] == ',')
					cursor = _code.find_first_not_of(separators, cursor+1);
			}
			return cursor+1;
		} else if('{' == _code[cursor]) {
			mType = DataType::dictionary;
			cursor = _code.find_first_not_of(separators, cursor+1);
			while(_code[cursor] != '}') {
				assert(_code[cursor] == '\"'); // Key stored as a string
				unsigned terminator = _code.find('\"', cursor+1);
				unsigned len = terminator - cursor - 1;
				auto element = make_pair(_code.substr(cursor+1,len), new Json());
				unsigned separator = _code.find(':', terminator+1);
				cursor = 1+ separator + element.second->initWithCode(_code.substr(separator+1));
				mDictionary.insert(element);
				cursor = _code.find_first_not_of(separators, cursor);
				if(_code[cursor] == ',')
					cursor = _code.find_first_not_of(separators, cursor+1);
			}
			return cursor+1;
		} else { // number
			unsigned terminator = _code.find_first_not_of("0123456789", cursor+1);
			if(_code[terminator] == '.') {
				mType = DataType::real;
				mFloat = (float)atof(_code.c_str());
				terminator = _code.find_first_not_of("0123456789", terminator+1);
			} else {
				mType = DataType::integer;
				mInt = atoi(_code.c_str());
			}
			return terminator;
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Json::openFromFile(const std::string& _fileName) {
		File jsonFile(_fileName);
		return Json(jsonFile.bufferAsText());
	}

	//------------------------------------------------------------------------------------------------------------------
	void Json::saveToFile(const std::string& _fileName) const {
		ofstream file(_fileName.c_str());
		file << serialize();
		file.close();
	}

	//------------------------------------------------------------------------------------------------------------------
	std::string Json::serialize() const {
		switch (mType)
		{
		case DataType::boolean:
			return mInt?"true":"false";
		case DataType::dictionary:
		{
			string dst = "{ "; // Empty space prevents smashing opening brace for empty dictionaries
			for(const auto& entry : mDictionary) {
				dst.append(string("\"")+entry.first+"\":"+entry.second->serialize()+",");
			}
			dst.back() = '}';
			return dst;
		}
		case DataType::list:
		{
			string dst = "[ "; // Empty space prevents smashing opening brace for empty lists
			for(const auto& entry : mList) {
				dst.append(entry->serialize()+",");
			}
			dst.back() = ']';
			return dst;
		}
		case DataType::text:
		{
			return string("\"")+mString+"\"";
		}
		case DataType::integer:
		{
			std::stringstream s;
			s << mInt;
			return s.str();
		}
		default:
			assert(false); // Unimplemented data type
			return "";
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	int Json::asInt() const {
		assert(isInt());
		return mInt;
	}

	//------------------------------------------------------------------------------------------------------------------
	int& Json::asInt() {
		assert(isInt());
		return mInt;
	}

	//------------------------------------------------------------------------------------------------------------------
	void Json::setInt(int _i) {
		mType = DataType::integer;
		mInt = _i;
	}

	//------------------------------------------------------------------------------------------------------------------
	const std::string& Json::asText() const {
		assert(isText());
		return mString;
	}

	//------------------------------------------------------------------------------------------------------------------
	std::string& Json::asText() {
		assert(isText());
		return mString;
	}

	//------------------------------------------------------------------------------------------------------------------
	void Json::setText(const std::string& _t) {
		mType = DataType::text;
		mString = _t;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Json::asBool() const {
		assert(isBool());
		return mInt != 0;
	}

	//------------------------------------------------------------------------------------------------------------------
	const Json& Json::operator[](const std::string& _key) const {
		assert(isDictionary());
		const auto& it = mDictionary.find(_key);
		assert(it != mDictionary.end());
		return *it->second;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json& Json::operator[](const std::string& _key) {
		assert(isDictionary());
		const auto& it = mDictionary.find(_key);
		if(it == mDictionary.end())
			mDictionary[_key] = new Json("");
		return *mDictionary[_key];
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Json::contains(const std::string& _key) const {
		assert(isDictionary());
		return mDictionary.find(_key) != mDictionary.end();
	}

	//------------------------------------------------------------------------------------------------------------------
	const Json::Dictionary& Json::asDictionary() const {
		assert(isDictionary());
		return mDictionary;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json::Dictionary& Json::asDictionary() {
		assert(isDictionary());
		return mDictionary;
	}

	//------------------------------------------------------------------------------------------------------------------
	const Json& Json::operator[](unsigned _idx) const {
		assert(isList());
		assert(_idx < mList.size());
		return *mList[_idx];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json& Json::operator[](unsigned _idx) {
		assert(isList());
		assert(_idx < mList.size());
		return *mList[_idx];
	}

	//------------------------------------------------------------------------------------------------------------------
	const Json::List& Json::asList() const {
		assert(isList());
		return mList;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json::List& Json::asList() {
		assert(isList());
		return mList;
	}

}	// namespace dmc