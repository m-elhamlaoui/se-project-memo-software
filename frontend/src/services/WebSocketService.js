import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

class WebSocketService {
  constructor() {
    this.stompClient = null
    this.subscriptions = {}
    this.isConnected = false
  }

  connect(username) {
    return new Promise((resolve, reject) => {
      // Disconnect existing connection
      this.disconnect()

      // Create new connection
      const socket = new SockJS("http://localhost:8081"+ '/ws')
      this.stompClient = Stomp.over(socket)

      this.stompClient.connect({}, (frame) => {
        this.isConnected = true
        console.log('Connected to WebSocket')
        resolve(this.stompClient)
      }, (error) => {
        this.isConnected = false
        console.error('WebSocket connection error:', error)
        reject(error)
      })
    })
  }

  disconnect() {
    if (this.stompClient) {
      // Unsubscribe from all existing subscriptions
      Object.values(this.subscriptions).forEach(sub => {
        if (sub) sub.unsubscribe()
      })
      this.subscriptions = {}

      // Disconnect the client
      this.stompClient.disconnect()
      this.stompClient = null
      this.isConnected = false
    }
  }

  /**
   * Subscribe to a specific channel
   * @param {string} destination - WebSocket destination
   * @param {function} callback - Callback to handle received messages
   * @param {string} [id] - Optional unique identifier for the subscription
   * @returns {object} Subscription object
   */
  subscribe(destination, callback, id) {
    if (!this.stompClient) {
      console.warn('WebSocket not connected')
      return null
    }

    // Generate a unique id if not provided
    const subscriptionId = id || `subscription_${Math.random().toString(36).substr(2, 9)}`

    // Subscribe and store the subscription
    const subscription = this.stompClient.subscribe(destination, (message) => {
      try {
        const payload = JSON.parse(message.body)
        callback(payload)
      } catch (error) {
        console.error(`Error processing message from ${destination}:`, error)
      }
    })

    // Store the subscription
    this.subscriptions[subscriptionId] = subscription

    return {
      id: subscriptionId,
      unsubscribe: () => {
        if (this.subscriptions[subscriptionId]) {
          this.subscriptions[subscriptionId].unsubscribe()
          delete this.subscriptions[subscriptionId]
        }
      }
    }
  }

  /**
   * Unsubscribe from a specific channel
   * @param {string} id - Subscription identifier
   */
  unsubscribe(id) {
    if (this.subscriptions[id]) {
      this.subscriptions[id].unsubscribe()
      delete this.subscriptions[id]
    }
  }
}

// Export a singleton instance
export default new WebSocketService()