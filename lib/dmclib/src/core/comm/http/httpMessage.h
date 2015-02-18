//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Feb-17
//----------------------------------------------------------------------------------------------------------------------
// Base class underlying http requests and responses
#ifndef _DMCLIB_CORE_COMM_HTTP_HTTPMESSAGE_H_
#define _DMCLIB_CORE_COMM_HTTP_HTTPMESSAGE_H_

#include <string>
#include <unordered_map>
#include <vector>

namespace dmc { namespace http {

	class Message {
	public:

		Message() = default;
		// Returns the number of bytes consumed in the string if the line is finished, 0 if still processing, -1 if an error occurred.
		int operator<<(const std::string& _raw);
		// Returns true when this message won't process any more text
		bool isComplete() const;

		// Accessors
		const std::unordered_map<std::string,std::string>&	headers			() const { return mHeaders; }
		std::unordered_map<std::string,std::string>&		headers			()		 { return mHeaders; }
		const std::string&									body			() const { return mBody; }
		void												setBody			(const std::string&);
		std::string											serialize		() const;

		// Wether this message specifies that the connection must be closed.
		bool												requiresClose	() const;

		// Known header labels
		static const std::string cContentLengthLabel;

	protected:
		bool												addHeader	(const std::string&);
		// Force completion when derived class constructs base message without using the parse mechanism
		void												setReady	() { mState = ParseState::Complete; }

	private:
		enum class ParseState {
			MessageLine,
			Headers,
			Body,
			Complete,
			Error
		};

		// Returns the number of bytes consumed in the string if the line is properly processed, 0 or negative if an error occurred.
		virtual int		processMessageLine	(const std::string& _raw) = 0;
		// Returns the number of bytes consumed in the string if the line is finished, 0 if still processing, -1 if an error occurred.
		bool			processHeaders		(const std::string&);
		bool			parseMessageLine	();
		bool			parseHeaders		();

		virtual void	serializeMessageLine(std::string& dst) const = 0;
		void			serializeHeaders	(std::string& dst) const;

	private:
		std::unordered_map<std::string,std::string>	mHeaders;
		std::string mBody;

		unsigned	mRequiredBodyLength = 0;
		ParseState	mState = ParseState::MessageLine;

	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_HTTPREQUEST_H_