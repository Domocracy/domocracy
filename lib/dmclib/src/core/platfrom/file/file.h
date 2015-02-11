//----------------------------------------------------------------------------------------------------------------------
// DmcLib
// Created by Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// 2014/Dec/27
//----------------------------------------------------------------------------------------------------------------------
// File implementation for platforms conforming to C++ standard
#ifndef _DMCLIB_CORE_PLATFORM_FILE_FILE_H_
#define _DMCLIB_CORE_PLATFORM_FILE_FILE_H_

#include <string>

namespace dmc {

	class File {
	public:
		File(const std::string& _path);
		~File();

		const void *	buffer		() const;
		const char *	bufferAsText() const;
		int				sizeInBytes	() const;

	private:
		unsigned	mSize = 0;
		void*		mBuffer = nullptr;
	};

	typedef File FileBase;

	//------------------------------------------------------------------------------------------------------------------
	// Inline implementation
	//------------------------------------------------------------------------------------------------------------------
	inline const void * File::buffer		() const { return mBuffer; }
	inline const char * File::bufferAsText	() const { return reinterpret_cast<const char*>(mBuffer); }
	inline int			File::sizeInBytes	() const { return mSize; }

}

#endif // _DMCLIB_CORE_PLATFORM_FILE_FILE_H_