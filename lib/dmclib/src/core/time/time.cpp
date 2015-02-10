//----------------------------------------------------------------------------------------------------------------------
// Domocracy
// Created by Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// On 26-Nov-2014
//----------------------------------------------------------------------------------------------------------------------
// Time
#include <cassert>
#include "time.h"

namespace dmc {
	//------------------------------------------------------------------------------------------------------------------
	// Static data definition
	//------------------------------------------------------------------------------------------------------------------
	Time* Time::sInstance = nullptr;	

	//------------------------------------------------------------------------------------------------------------------
	// Method implementations
	//------------------------------------------------------------------------------------------------------------------
	Time* Time::get() {
		if(!sInstance)
			sInstance = new Time;
		return sInstance;
	}

	//------------------------------------------------------------------------------------------------------------------
	Time::Time() {
		#if defined (__linux__)
			// Get current time
			gettimeofday(&mInitTime, 0);
		#elif defined (_WIN32)
			// Get initial time
			QueryPerformanceCounter(&mInitTime);
		#endif
	}

	//------------------------------------------------------------------------------------------------------------------
	double Time::getTime() {
	#if defined (__linux__)
			// Get current time
			timeval currentTime;
			gettimeofday(&currentTime, 0);
			return double(currentTime.tv_sec - mInitTime.tv_sec) + double(currentTime.tv_usec - mInitTime.tv_usec) / 1000000;
	#elif defined (_WIN32)
			// Get current time
			LARGE_INTEGER largeTicks;
			QueryPerformanceCounter(&largeTicks);
			unsigned currTime = largeTicks.LowPart;
			// Convert time difference to seconds
			LARGE_INTEGER frequency;
			QueryPerformanceFrequency(&frequency);
			return (double(currTime) / double(frequency.LowPart)) -
				(double(mInitTime.LowPart) / double(frequency.LowPart));
	#endif 
	}
	
}	// namespace dmc