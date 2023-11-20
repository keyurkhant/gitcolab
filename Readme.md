# **GitHub Colab**

## **Project Overview** 
*GitHub Colab* is a project aimed at enhancing the experience of open-source developers. More than 90% of developers use GitHub for their open-source projects, and integrating various productivity and developer tools with GitHub can enhance their overall experience and productivity. Our application offers an all-in-one platform to start new projects, invite skilled contributors, and explore other open-source projects from developers across the globe.

Our primary target users are open-source developers who seek a unified platform that seamlessly integrates various development tools and services, thereby making the management of open-source projects easier and more efficient.

## **Application Workflow and Features**

### 1. User Authentication
The system supports new user registration and existing user login. It has a forget password functionality for recovering access. Repository owners can invite contributors through the system, sending an invitation link to the contributor via email. Developers can access a project's dashboard if they are existing users on our application. Otherwise, they need to follow the necessary registration steps first.

### 2. User Profile and Role Management
Users can maintain their profiles, view and update user information, and manage system access based on roles. The roles include Repository Owner and Contributor, and a user can be both a repository owner as well as a project contributor. Users have access to all repositories and dashboards for which they are contributors.

### 3. Integration
The system provides OAuth support for integrating GitHub and Atlassian accounts. Users can create a new repository from our system and integrate the repository with Jira and Confluence. The workflow allows for creating Jira issues or tickets when an issue is raised in a GitHub repository.

### 4. Analytics Dashboard
Our system provides a comprehensive analytics dashboard displaying various statistics of the GitHub repository and Jira board. Depending on the user's role, they can access relevant repository statistics. The dashboard presents various graphs and plots for a visual presentation of the data.

### 5. Explore
Users can explore active open-source repositories from other users who deviate by 1 to 3 degree connections from them. Users can also request a repository owner to become a collaborator of the project via the explore page.

## **Running the Project**
The front-end of the project is built using React. To run the front-end, navigate to the `gc-client` directory and run the following commands:
```
npm install
npm start
```
This will start the front-end on `localhost:3000`.

The backend is developed using Java Spring Boot. To run the back-end, navigate to the `gc-server` directory and run the following commands:
```
mvn compile
mvn spring-boot:run
```
This will start the back-end server.

To run tests for the front-end, use the following command in the `gc-client` directory:
```
npm test
```
For back-end tests, use the following command in the `gc-server` directory:
```
mvn test
```

## **CI/CD Pipeline Overview**
GitHub Colab uses GitLab CI/CD for continuous integration and continuous deployment. The pipeline consists of three stages: Test, Build, and Deploy.

1. **Test**: This stage ensures all unit tests pass. It runs `mvn test` for the back-end and `npm test` for the front-end.
2. **Build**: This stage compiles and prepares the project for deployment. It runs `mvn compile` for the back-end and `npm run build` for the front-end.
3. **Deploy**: This stage deploys the application. It uses `npx netlify deploy` to deploy the front-end to Netlify.

The pipeline ensures every code push or merge request is tested and built before deployment, increasing the reliability of the deployed code and facilitating continuous improvement.

Our CI/CD pipeline deploys the project to Netlify. The deployed application can be found [here](https://keen-rolypoly-f595cb.netlify.app/).

## **CI/CD Pipeline Caching**

Caching in our CI/CD pipeline boosts build speeds by storing and reusing heavy-to-fetch data.

Configured within the `gitlab-ci.yml` file, we cache the following paths:

- `.m2/repository`: Holds Maven project dependencies, avoiding repeated internet downloads.
- `gc-client/node_modules`: Contains Node.js dependencies of our front-end application, eliminating the need for constant downloads.
- `gc-client/build`: Stores the compiled front-end code, expediting the deployment process.

This approach promotes pipeline efficiency by limiting unnecessary recompilation and redownloading, thus conserving time and bandwidth.

## **Technologies Used Till now**
- Java Spring Boot
- JUnit
- React
- React-Bootstrap
- HTML & CSS
- GitLab CI/CD
