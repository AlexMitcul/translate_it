# Translation Application

---




## Running the Application
1. Clone the repository
    ```bash
    git clone https://github.com/AlexMitcul/translate_it.git
    cd translate_it
    ```
2. Build the project
    ```bash
    mvn clean install
    ```
3. Run the application
    ```bash
    mvn spring-boot:run
    ```


### Or just use the Makefile command

# Makefile for Docker Compose

This Makefile provides a simple interface for managing Docker Compose projects. It defines common tasks to build, start, stop, and rebuild your Docker containers.

## Targets

- **`build`**  
  Builds the Docker images defined in the `docker-compose.yml` file.

  ```sh
  make build
  ```

- **`up`**  
  Starts the Docker containers in detached mode using the `docker-compose.yml` file and an optional environment file (`.env`).

  ```sh
  make up
  ```

- **`down`**  
  Stops and removes the Docker containers defined in the `docker-compose.yml` file.

  ```sh
  make down
  ```

- **`rebuild`**  
  Stops the containers, rebuilds the Docker images, and starts the containers again.

  ```sh
  make rebuild
  ```

## Configuration

- **`DC_PATH`**  
  Path to the `docker-compose.yml` file. Default is `./docker-compose.yml`.

- **`ENV_FILE_PATH`**  
  Path to the `.env` file for environment variables. Default is `.env`.

## Usage

To use this Makefile, navigate to the directory containing it and run one of the targets with the `make` command. For example, to build and start the containers, use:

```sh
make rebuild
```

## Notes

- All targets are marked as `.PHONY` to ensure that they are always executed regardless of file names.
- Ensure Docker and Docker Compose are installed and properly configured on your system.

Feel free to modify the `DC_PATH` and `ENV_FILE_PATH` variables if your file paths are different.

## License
This project is licensed under the MIT License.
