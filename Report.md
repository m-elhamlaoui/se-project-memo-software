# Task Management System

## Overview
A cutting-edge task management platform that combines traditional task organization with blockchain technology to provide a transparent and democratic decision-making process. It supports a flexible voting system to handle task-related decisions efficiently.

---

## Features

### Core Functionality
- **Task Management**
  - CRUD (Create, Read, Update, Delete) operations for tasks and task blocks
  - Track and update task progress, status, and completion
  - Set deadlines, priorities, and reminders for tasks
  - Organize tasks hierarchically under specific task blocks

### Voting System
- **Customizable Voting Mechanism**
  - Runtime-adjustable strategies for task or project approval
  - Configurable voting duration (e.g., from minutes to days)
  - Adjustable voting thresholds (percentage-based approvals)
  - Real-time updates on vote status and results
  - Flexible decision-making for task block modifications or task deletion

### User Management
- **Multi-User Support**
  - Secure user registration and authentication with password encryption
  - Task block membership management (add/remove members)
  - Invitation system for onboarding new members (future release)
  - Role-based access control (admin, editor, viewer roles planned)

---

## Technical Architecture

### Backend Structure
`````
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── taskblock/
│   │               ├── config/
│   │               ├── controller/
│   │               │   
│   │               ├── dto/
│   │               ├── exceptions/
│   │               ├── model/
│   │               
│   │               ├── repository/
│   │               │   
│   │               │   
│   │               ├── response/
│   │               └── service/
│   │               └── TaskBlock.java
│   └── resources/
│       └── application.properties

`````

### Design Patterns

1. **Singleton Pattern**
   - Manages blockchain and database connection instances
   - Ensures that critical system components (e.g., blockchain wallets) have a single instance across the system

2. **Strategy Pattern**
   - Allows dynamic adjustment of voting strategies (e.g., quorum-based, majority-based)
   - Configurable at runtime to support custom use cases
   - Ensures flexibility for different projects and teams

3. **Observer Pattern (Frontend & Backend)**
   - Backend:
     - Handles real-time notifications for task changes and voting results
     - Automatically updates users when a vote is finalized or a task is assigned
   - Frontend:
     - WebSocket-based implementation for real-time updates
     - Dynamically updates the task list, voting status, and notifications without requiring a page refresh

4. **Facade Pattern (Frontend)**
   - Simplifies state management and WebSocket handling
   - Centralizes communication between the Vuex store and WebSocket connection
   - Provides an easy-to-use interface for updating the state and subscribing to WebSocket events

---

## Blockchain Integration

- **Custom Blockchain Server**
  - Built using **Go**
  - Deployed and hosted with **Docker**
  - Secure transaction handling and task block verification
  - Integrated API endpoints for interacting with the blockchain server
- **Features:**
  - Task verification and immutable record storage
  - Wallet association and identity management
  - Handles voting mechanism securely

---

### Data Relationships

- **TaskBlock**
  - Contains multiple tasks and is associated with multiple wallets
  - Represents a group or project with its own tasks and members

- **Wallet**
  - Uniquely identifies users in the blockchain system
  - Supports multiple wallet associations for each user

- **User**
  - Can own multiple wallets
  - Can belong to multiple TaskBlocks
  - Tracks roles and permissions

### Frontend WebSocket and Store Integration
- **WebSocket Integration:**
  - Handles real-time communication for updates like task changes and voting results
  - Automatically triggers UI updates for users
- **State Management with Facade Pattern:**
  - Vuex store acts as the single source of truth
  - Facade simplifies interactions with the WebSocket, abstracting complex subscription logic
  - Ensures seamless integration between state changes and UI updates

---

## Planned Features

### Notification System
- **Real-Time Alerts**
  - Push notifications for task changes, voting progress, and results
  - Email or SMS alerts for critical updates
- **Task Assignment Alerts**
  - Notify users when assigned a new task
  - Send reminders for upcoming task deadlines

### Enhanced User Management
- **Advanced Role Management**
  - Define custom roles with specific permissions
  - Assign roles dynamically
- **Activity Tracking**
  - Log user activities for auditing purposes

---

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Spring Boot Framework
- Database (PostgreSQL or MySQL recommended)
- Blockchain server setup using **Go** and Docker
- Node.js and Vue.js for frontend

### Installation Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/task-management.git
2. Build the project ( Go to the backend directory and run this command )
    ```bash
   mvn clean install 
3. Install Vue.js frontend dependencies:
    ```bash
        cd frontend
        npm install
        npm run serve
4. Access the application at http://localhost:8080.