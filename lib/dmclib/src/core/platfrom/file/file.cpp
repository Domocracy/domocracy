//----------------------------------------------------------------------------------------------------------------------
// Revolution Engine
// Created by Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// 2014/April/13
//----------------------------------------------------------------------------------------------------------------------
// File implementation for platforms conforming to C++ standard

#include <cassert>
#include <fstream>

#include "file.h"

using namespace std;

namespace dmc {

	//--------------------------------------------------------------------------------------------------------------
	File::File(const string& _path) {
		mPath = _path;
	}

	//--------------------------------------------------------------------------------------------------------------
	File::~File() {
		if(mMustWrite)
		{
			fstream dstFile(mPath);
			dstFile.write(static_cast<const char*>(mBuffer), mSize);
			dstFile.close();
		}
		if (mBuffer)
			delete static_cast<const char*>(mBuffer); // Static cast prevents undefined behavior deleting void*
	}

	//--------------------------------------------------------------------------------------------------------------
	File* File::openExisting(const string& _path) {
		ifstream f(_path.c_str());
		if (f.good()) {
			f.close();
			return new File(_path);
		}
		return nullptr;
	}

	//--------------------------------------------------------------------------------------------------------------
	void File::readAll() {
		fstream srcFile;
		// Open the file
		srcFile.open(mPath.c_str(), ios_base::binary | ios_base::out);
		assert(srcFile.is_open());
		// Meassure it's size
		srcFile.seekg(0, ios::end);
		mSize = int(srcFile.tellg());
		srcFile.seekg(0, ios::beg);
		int begin = int(srcFile.tellg());
		mSize -= begin;
		// Allocate the buffer
		mBuffer = new char[mSize + 1];
		// Fill the buffer with the contents of the file
		srcFile.read((char*)mBuffer, mSize);
		((char*)mBuffer)[mSize] = '\0';

		srcFile.close();
	}

	//--------------------------------------------------------------------------------------------------------------
	void File::setContent(const void* _buffer, size_t _size) {
		if(mBuffer) {
			delete static_cast<const char*>(mBuffer);
		}
		mSize = _size;
		mBuffer = _buffer;
		mMustWrite = true;
	}

}