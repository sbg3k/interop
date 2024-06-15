import jpype
import jpype.imports
from jpype.types import *

# Start the JVM
jpype.startJVM(classpath=["./rascal-wrapper.jar", "./lib/rascal-shell-stable.jar", "./lib/rascal.jar", "./lib/rascal-core.jar"])

# Import the necessary classes
RascalWrapper = jpype.JClass("RascalWrapper")
ValueFactory = jpype.JClass("io.usethesource.vallang.impl.persistent.ValueFactory")

# Create an instance of RascalWrapper
wrapper = RascalWrapper()

# Get the IValueFactory instance
valueFactory = ValueFactory.getInstance()

# Execute the example Rascal script to ensure the module is loaded
wrapper.executeRascalFile("./Main.rsc")

# Define the arguments for the add function
arg1 = valueFactory.integer(5)
arg2 = valueFactory.integer(3)

# Call the add function from the Example module
result = wrapper.callFunction("Main", "main", arg1, arg2)

# Print the result
print("Result from add function:")
print(result)

# Get the stdoutput
# output = wrapper.getOutput()
# print("Output from Java:")
# print(output)

# Shutdown the JVM
jpype.shutdownJVM()
