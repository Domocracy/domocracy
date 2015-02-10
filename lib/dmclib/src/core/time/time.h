//----------------------------------------------------------------------------------------------------------------------
// Domocracy
// Created by Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// On 26-Nov-2014
//----------------------------------------------------------------------------------------------------------------------
// Time
#ifndef _DMCSLIB_CORE_TIME_TIME_H_
#define _DMCSLIB_CORE_TIME_TIME_H_

#if defined(__linux__)
	#include <sys/time.h>
#elif defined(_WIN32)
	#include <Windows.h>
#endif

#include <iostream>

namespace dmc {
	class Time {
	public:
		// --- Singleton life cycle ---
		static Time* get();        // Returns the singleton instance

	public: // --- Public interface ---
		double	getTime	();			//getTime in seconds
		void	sleep	(unsigned _seconds);

	private:
		Time();
		~Time() = delete;
	private:
		// Singleton instance
		static Time* sInstance; // Static data definition
		// members
		#if defined(__linux__)
			timeval mInitTime;
		#elif defined (_WIN32)
			LARGE_INTEGER mInitTime;
		#endif
	};

}        // namespace dmc

#endif // _DMCSLIB_CORE_TIME_TIME_H_