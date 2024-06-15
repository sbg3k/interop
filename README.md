# Interop between Rascal and Python

This repository contains scripts to prepare the stage and execute a Rascal script using JPype.

## Files

- `download_dependencies.py`: Script to download the necessary JAR files and set up the environment.
- `execute_rascal.py`: Script to execute a Rascal script using the downloaded JAR files and JPype.
- `requirements.txt`: File specifying the required Python packages.
- `README.md`: This readme file.

## Prerequisites

- Python 3.x
- Internet connection to download the JAR files

## Setup

1. Clone this repository to your local machine.
2. Navigate to the directory where you cloned the repository.

### Step 1: Install Requirements

Install the required Python packages using `pip`:

```sh
pip install -r requirements.txt
```

### Step 2: Download Necessary JAR Files

Run the `download_dependencies.py` script to download the required JAR files and place them in the `lib` directory.

```sh
python3 download_dependencies.py
```

This script will:
- Create a `lib` directory if it doesn't exist.
- Download the following JAR files:
  - `rascal-shell-stable.jar`
  - `rascal-0.40.2.jar`
  - `rascal-core-0.9.2-BOOT2.jar`
- Place the downloaded JAR files in the `lib` directory.

### Step 3: Execute the Rascal Script

Run the `execute_rascal.py` script to execute the Rascal script using JPype.

```sh
python3 execute_rascal.py
```

This script will:
- Start the JVM with the necessary classpath.
- Import and create instances of the required classes.
- Execute the Rascal script (`Main.rsc`).
- Call the `main` function from the `Main` module with example arguments.
- Print the result of the function call.
- Shutdown the JVM.

## Notes

- Ensure that `Main.rsc` is present in the same directory as `execute_rascal.py` or provide the correct path in the script.
- Modify the `execute_rascal.py` script as needed to fit your use case.

## Troubleshooting

- If there are issues with downloading the files, check your internet connection and ensure the URLs are correct.
- If there are issues with JPype, ensure it is installed correctly (`pip install jpype1`) and compatible with your Python version.
- Check the paths specified in `execute_rascal.py` to ensure they point to the correct JAR files and Rascal script.