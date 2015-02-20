//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2014-Dec-22
//----------------------------------------------------------------------------------------------------------------------
// json object
#ifndef _DMCLIB_CORE_COMM_JSON_JSON_H_
#define _DMCLIB_CORE_COMM_JSON_JSON_H_

#include <unordered_map>
#include <string>
#include <vector>

namespace dmc {
	class Json {
	public:
		// Internal types
		typedef std::unordered_map<std::string, Json*>	Dictionary;
		typedef std::vector<Json*>	List;

		// Construction and asignment
		Json() = default;
		~Json();
		explicit Json(const std::string& _code);
		Json(const Json&);
		Json& operator=(const Json&);

		// Serialize
		static Json	openFromFile	(const std::string& _fileName);
		void		saveToFile		(const std::string& _fileName) const;
		void		operator<<		(const std::string& _code);
		std::string serialize		() const;


		// Type deduction
		bool isNill			() const { return mType == DataType::nill; }
		bool isInt			() const { return mType == DataType::integer; }
		bool isFloat		() const { return mType == DataType::real; }
		bool isText			() const { return mType == DataType::text; }
		bool isDictionary	() const { return mType == DataType::dictionary; }
		bool isList			() const { return mType == DataType::list; }
		bool isBool			() const { return mType == DataType::boolean; }

		// Simple getters and setters
		int					asInt		() const;
		int&				asInt		();
		void				setInt		(int);
		float				asFloat		() const;
		float&				asFloat		();
		void				setFloat	(float);
		const std::string&	asText		() const;
		std::string&		asText		();
		void				setText		(const std::string&);
		bool				asBool		() const;
		void				setBool		(bool);

		// Dictionary accessors
		const Json&			operator[]	(const std::string& _key) const;
		Json&				operator[]	(const std::string& _key);
		bool				contains	(const std::string& _key) const;
		const Dictionary&	asDictionary() const;
		Dictionary&			asDictionary();
		// List accessors
		const Json&			operator[]	(unsigned _idx) const;
		Json&				operator[]	(unsigned _idx);
		void				push_back	(const Json& _obj);
		const List&			asList		() const;
		List&				asList		();

	private:
		unsigned initWithCode(const std::string& _code); // Returns number of parsed characters

		enum class DataType {
			nill,
			integer,
			real,
			text,
			dictionary,
			list,
			boolean
		} mType = DataType::nill;

		std::string mString;
		int			mInt;
		float		mFloat;
		Dictionary	mDictionary;
		List		mList;
	};
}

#endif // _DMCLIB_CORE_COMM_JSON_JSON_H_