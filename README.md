screen-mirror-server (Spt=ring-Boot)

Overview 
* This is the relay server for real-time screen mirroring system.

Responsibilites:
* Accept screen frames from sender
* Broadcast frames to viewers
* Enforce backpressure & stability

  Note: server does not capture screens.

Architecture of full Screen Mirror App

Local Sender (WS) --> Backend (WS) --> Viewer (WS)

Tech Stack
* Java 17+
* Spring Boot
* Spring WebSocket
* Embedded Tomcat
* Docker (Render)

Endpoints
Backend (server) : https://screen-mirror-server.onrender.com

Frontend (UI)    : https://screenmirrorintt.netlify.app/

Backpressure Strategy
* No frame queue
* Drop frames if:
  * Viewer send buffer is full
  * Viewer is slow
* Sender FPS throttled

  This prevents:
   * OutOfMemoryError
   * WebSocket 1006 errors
   * Server crashes

Deployment 
 * platform: Render
 * Type:Docker Web service
 * Port:8080

Known Limitations
* No persistance
* No auth
* No Turn / NAT traversal
* Not optimised & developed for Production scale

Planned Improvements
* JWT-based room auth
* Sender heartbeat
* Viewer stats
* WebRTC signaling
