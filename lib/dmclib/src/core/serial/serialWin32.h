////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/25
// Author:	Carmelo J. Fdez-Agüera Tortosa 
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Simple serial port communications
#ifndef _DMCLIB_CORE_SERIAL_SERIALWIN32_H_
#define _DMCLIB_CORE_SERIAL_SERIALWIN32_H_

#ifdef _WIN32

#include <Windows.h>
#include <cstdint>

namespace dmc {

	class SerialWin32 {
	public:
		unsigned	write	(const void* _src, unsigned _nBytes);
		bool		write	(uint8_t);

		unsigned	read	(void * _dst, unsigned _nBytes); 
		uint8_t		read	(); 


	protected:
		SerialWin32			(const char* _port, unsigned _baudRate);

	private:
		void openPort		(const char* _port);
		void setBaudRate	(unsigned _baudRate);
		
	private:
		HANDLE		mPortHandle;
	};

	typedef SerialWin32 SerialBase;

}	// namespace dmc

#endif // _WIN32
#endif // _DMCLIB_CORE_SERIAL_SERIALWIN32_H_