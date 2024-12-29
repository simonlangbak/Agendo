# Agendo - the simple TODO app

## Overview

![image](https://github.com/user-attachments/assets/6c274046-5569-4a63-a8da-7ca9d7f0c310)

Agendo is a straightforward TODO application built as a full-stack learning and practice project. It provides a hands-on environment for exploring modern web development concepts. Feel free to fork this repository and use it as your own playground.

## Features

Agendo currently offers the following core functionalities:
* **Secure Authentication and Authorization:** Implements JWT-based authentication and authorization to protect both frontend routes and backend controllers, ensuring only authorized users can access protected resources.
* **Board Management:** Users can create and delete boards to organize their tasks effectively. Boards serve as containers for tasks, providing a visual structure for project management.
* **Task Management:** Users can add tasks to specific boards and move them between columns to track their progress. This Kanban-style approach allows for clear visualization of task status ("To Do", "In Progress", "Review", "Done").

The app may be extended with more features in the future. The following are potential future additions to Agendo:

* Full CRUD functionality for boards and tasks
* Task prioritization
* Change the rows in a board
* Assign users to specific tasks
* Due dates with an option to notify users 
* Search functionality to find a given task

## Getting Started

### Using docker (recommended - used for running the project)

TODO. 
To follow cloud native principles, docker...

### Manually running the backend and frontend (used during development)
TODO.

## Tech stack

Agendo is built using the following technologies:

*   **Backend:** Spring Boot - A powerful Java-based framework for building web applications.
*   **Database:** SQL (Vendor agnostic) - The application is designed to be compatible with various SQL databases. Specific database configuration is handled through configuration (CLI arguments, environment variables or application.yaml)
*   **Frontend:** Angular - A popular TypeScript-based framework for building dynamic and responsive user interfaces.
*   **UI Component Library:** PrimeNG - A rich set of UI components for Angular, providing pre-built and customizable elements for a polished user experience.

## Project Structure

The project is organized into the following main modules:

*   **Backend (see [services/agendo-backend](services/agendo-backend)):** This directory contains all backend-related code, implemented using Spring Boot. Currently, the backend is structured as a monolith to simplify development and deployment. Future iterations may explore a microservice architecture to enhance scalability and maintainability.
*   **Frontend (see [agendo-ui](agendo-ui)):** This directory houses all frontend code, built with Angular and utilizing the PrimeNG component library.

## Contributing 
Contributions are welcome! If you'd like to contribute to Agendo, please open an issue or submit a pull request.
