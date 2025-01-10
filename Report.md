# Task Management System Documentation

## Project Overview

The Task Management System is a revolutionary platform that combines traditional task management capabilities with blockchain technology to create a secure, transparent, and democratic workspace. At its core, the system enables teams to collaborate on tasks while ensuring fair decision-making through a sophisticated voting mechanism and secure user authentication via blockchain wallets.

The system's unique approach lies in its integration of blockchain technology for user authentication and voting processes, making it particularly suitable for decentralized teams and organizations that value democratic decision-making. Each user action is secured through their personal blockchain wallet, ensuring accountability and traceability of all decisions made within the system.


## Core Components

### TaskBlock
A TaskBlock serves as the primary organizational unit within the system. Think of it as a secure, collaborative workspace where team members come together to manage tasks and make decisions. Key characteristics include:

- Self-contained workspace for specific projects or teams
- Democratic decision-making through integrated voting system
- Secure member authentication via blockchain wallets
- Customizable voting parameters and thresholds
- Support for multiple working groups within the same TaskBlock

### Groups
Groups represent specialized subsets of members within a TaskBlock. They enable efficient task distribution and management by:

- Organizing members based on roles, skills, or responsibilities
- Facilitating focused communication within specific team segments
- Allowing targeted task assignments
- Supporting hierarchical team structures
- Managing access permissions at a granular level

Example: A software development TaskBlock might contain groups like "Frontend Developers," "Backend Developers," and "QA Team."

### Tasks
Tasks are the fundamental units of work within the system. They are characterized by:

- Clear ownership and accountability
- Voting-based approval and completion verification
- Flexible assignment to individuals or groups
- Priority and deadline management
- Progress tracking and status updates
- Tagging and categorization capabilities

### Voting System
The voting system forms the backbone of democratic decision-making within the platform. It features:

- Multiple voting strategies (e.g., majority, unanimous, weighted)
- Configurable voting durations
- Adjustable approval thresholds
- Blockchain-based vote recording
- Real-time voting progress tracking
- Automatic result execution

### Notification System
The notification system ensures effective communication and awareness across the platform through:

- Real-time updates on task changes
- Voting deadline reminders
- Assignment notifications
- Group invitation alerts
- Status change broadcasts
- Custom notification preferences

## Blockchain Integration

The system leverages blockchain technology for:

### Authentication and Security
- Secure user identification through personal wallets
- Immutable activity logging
- Transparent decision tracking
- Cryptographic security for all transactions

### Voting Mechanism
- Tamper-proof vote recording
- Verifiable voting results
- Transparent decision history
- Secure vote counting and tabulation

## Design Patterns

### Singleton Pattern
Used for managing critical system components that require single-instance control:
- Database connections
- Blockchain interface
- Configuration management
- Cache management

### Strategy Pattern
Implements flexible, interchangeable algorithms for:
- Voting mechanisms
- Task assignment strategies
- Notification delivery methods
- Permission management
![alt text](image.png)
### Observer Pattern
Enables reactive system behavior through:
- Real-time notification delivery
- Task status monitoring
- Vote progress tracking
- Member activity observation
- Automatic updates across dependent components

## Data Relationships

### TaskBlock Relationships
- Contains multiple groups and tasks
- Associates with multiple member wallets
- Maintains voting configurations
- Tracks member permissions

### Group Relationships
- Belongs to one TaskBlock
- Contains multiple members
- Associates with multiple tasks
- Maintains group-specific settings

### Task Relationships
- Belongs to one TaskBlock
- Associates with multiple groups or members
- Contains multiple votes
- Generates multiple notifications

### User-Wallet Relationships
- One user can have multiple wallets
- Each wallet belongs to one user
- Wallets can participate in multiple TaskBlocks
- Wallets maintain voting rights

## System Features

### Task Management
- Creation and assignment
- Progress tracking
- Priority management
- Deadline monitoring
- Status updates
- Task dependencies

### User Management
- Secure authentication
- Role-based access control
- Group membership
- Activity tracking
- Profile management

### Voting Management
- Strategy configuration
- Duration setting
- Threshold management
- Result processing
- Vote recording

### Notification Management
- Event monitoring
- Message generation
- Delivery prioritization
- User preferences
- Real-time updates

## Future Enhancements

### Planned Features
- Advanced analytics dashboard
- Custom workflow creation
- Integrated chat system
- Mobile application
- API expansion
- Enhanced reporting capabilities

### Technical Improvements
- Performance optimization
- Scalability enhancements
- Additional blockchain integrations
- Extended API capabilities
- Enhanced security features


---
## Backend Structure
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
│   ├──resources/
│   |    └── application.properties
|   └──test/
|        └───...
`````

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
2.  Set up your mysql credentials **(username , password)** in the application.properties **(resources/application.properties)** . 

3. Build the project ( Go to the backend directory and run this command )
    ```bash
    mvn clean install 
4. Install Vue.js frontend dependencies:
    ```bash
        cd frontend
        npm install
        npm run serve
5. Access the application at http://localhost:8080.
