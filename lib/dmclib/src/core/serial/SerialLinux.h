////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/25
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Simple serial port communications
#ifndef _DMCLIB_CORE_SERIAL_SERIALLINUX_H_
#define _DMCLIB_CORE_SERIAL_SERIALLINUX_H_

#if defined(__linux__) || defined(__Raspi__)

#include <cstdint>
extern "C" {
#include <termios.h>
}

namespace dmc {

	class SerialLinux {
	public:
		unsigned	write		(const void* _src, unsigned _nBytes);
		bool		write		(uint8_t);

		unsigned	read		(void * _dst, unsigned _nBytes); // Returns the amount of bytes read
		uint8_t		read		(); // Reads one byte

	protected:
		SerialLinux						(const char* _port, unsigned _baudRate);
		void		setBlocking			(bool);
		void		clearInputBuffer	();
		void		openPortFile		(const char* _port);
		void		setBaudRate			(struct termios* _serialPortInfo, unsigned _baudRate, const char* _port);
		
	private:
		int				mFileDesc;
		struct termios*	mPortConfig;
	};

	typedef SerialLinux SerialBase;

}	// namespace dmc

#endif // __linux__ || __Raspi__
#endif // _DMCLIB_CORE_SERIAL_SERIALLINUX_H_
