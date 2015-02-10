////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCLIB_SERVICE_SOCKET_SOCKETMGR_H_
#define _DMCLIB_SERVICE_SOCKET_SOCKETMGR_H_

#ifdef _WIN32
#include <winsock2.h>
#include <ws2tcpip.h>
#endif // _WIN32
#ifdef __linux__
	#include <unistd.h>
	#include <sys/types.h>
	#include <sys/socket.h>
	#include <netdb.h>
	typedef int SOCKET;
#endif // __linux__

#include <functional>
#include <map>
#include <mutex>
#include <string>
#include <thread>
#include <vector>

namespace dmc {

	class ServerSocket;

	// 666 TODO: Rewrite this class using core/comm/socket/socketServer
	class SocketMgr {
	public:
		// Singleton interface
		static void			init	(unsigned _port);
		static SocketMgr*	get		();

		void		update					(); // House keeping

		// Write a message to the specified client
		// Return true on exit, false on fail
		bool		write					(unsigned _clientId, const std::string& _msg) const;
		// Return true if we read anything from anyone
		bool		readAny					(unsigned& _client, std::string& _msg) const;
		bool		readFrom				(unsigned _client, std::string& _msg) const;

		void		ownConnection			(unsigned _client); // Prevents messages from this client to be catched by readAny
		void		releaseConnection		(unsigned _client); // Relinquishes ownership of the connection, so it's messages will show in readAny requests again
		bool		closeConnection			(unsigned _client);

		bool		isConnectionAlive		(unsigned _client) const;
		void		onNewConnection			(std::function<void(unsigned)>);

	private:
		SocketMgr							(unsigned _port);
		addrinfo*	buildAddresInfo			(unsigned _port);
		void		startListening			(const addrinfo* _socketAddress);
		void		createConnection		(int _socketDesc);
		void		cleanDeadConnections	();

	private:
		unsigned							mPort;
		SOCKET								mListener;
		mutable std::mutex					mConMutex;
		std::function<void(unsigned)>		mOnNewConnection;
		std::thread							mListenThread;
		std::map<unsigned, ServerSocket*>	mActiveConnections;
		std::vector<ServerSocket*>			mDeadConnections;

		static SocketMgr*	sInstance;
	};

}

#endif // _DMCLIB_SERVICE_SOCKET_SOCKETMGR_H_
