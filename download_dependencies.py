# download jar from https://update.rascal-mpl.org/console/rascal-shell-stable.jar as rascal-shell-stable.jar
# download jar from https://releases.usethesource.io/maven/org/rascalmpl/rascal/0.40.2/rascal-0.40.2.jar as rascal.jar
# download jar from https://releases.usethesource.io/maven/org/rascalmpl/rascal-core/0.9.2-BOOT2/rascal-core-0.9.2-BOOT2.jar as rascal-core.jar
# want to download all these and put them in the lib folder with python requests

import requests, os

def download_file(url, filename):
    response = requests.get(url)
    with open(filename, "wb") as file:
        file.write(response.content)
    
def main():
    if not os.path.exists("lib"):
        os.mkdir("lib")
    
    download_file("https://update.rascal-mpl.org/console/rascal-shell-stable.jar", "./lib/rascal-shell-stable.jar")
    download_file("https://releases.usethesource.io/maven/org/rascalmpl/rascal/0.40.2/rascal-0.40.2.jar", "./lib/rascal.jar")
    download_file("https://releases.usethesource.io/maven/org/rascalmpl/rascal-core/0.9.2-BOOT2/rascal-core-0.9.2-BOOT2.jar", "./lib/rascal-core.jar")

if __name__ == "__main__":
    main()