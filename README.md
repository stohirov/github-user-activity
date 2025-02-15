# GitHub User Activity CLI

A simple Java command-line tool that fetches and displays a GitHub user's recent activity using the GitHub API.

## Features

- Fetches recent public events of a GitHub user
- Displays formatted JSON output
- Uses Gson for JSON parsing

## Prerequisites

Ensure you have the following installed:

- Java (JDK 11 or later)
- Maven (if using dependencies via pom.xml)

## Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/stohirov/github-user-activity.git
   cd github-user-activity
   ```
2. Build the project:
   ```sh
   javac -cp gson-2.11.0.jar src/main/java/org/example/GithubUserCli.java -d bin
   ```
3. Run the CLI tool:
   ```sh
   java -cp bin:gson-2.11.0.jar org.example.GithubUserCli <github-username>
   ```

## Example Usage

```sh
java -cp bin:gson-2.11.0.jar org.example.GithubUserCli octocat
```

*Output:*

```json
{
  "type": "PushEvent",
  "repo": "octocat/Hello-World",
  "created_at": "2025-02-15T17:07:24Z"
}
```

## Dependencies

This project uses Gson for JSON parsing. If using Maven, add this to `pom.xml`:

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.11.0</version>
</dependency>
```

## Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature-name`)
3. Commit your changes (`git commit -m 'Add feature'`)
4. Push to your branch (`git push origin feature-name`)
5. Open a pull request

[Roadmap.sh](https://roadmap.sh/projects/github-user-activity](https://roadmap.sh/projects/github-user-activity)
