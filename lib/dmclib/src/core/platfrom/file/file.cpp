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
		fstream srcFile;
		// Open the file
		srcFile.open(_path.c_str(), ios_base::binary | ios_base::in);
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
	File::~File() {
		if (mBuffer)
			delete static_cast<char*>(mBuffer); // Static cast prevents undefined behavior deleting void*
	}
}