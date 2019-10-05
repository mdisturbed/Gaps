# Gaps
Searches through your Plex Server or local folders for all movies, then queries for known movies in the same collection. If those movies don't exist in your library, Gaps will recommend getting those movies, legally of course.

## Usage
### Local Requirements

It's a Spring Boot App and you need Java to run this if you want to use the jar. You'll need to make sure Java is accessible in your command window or terminal. You can also run as a docker container, in which case, you would need Docker. 

#### Windows 
##### Jar
This is a good write up on getting Java in the path variable on [windows](https://javatutorial.net/set-java-home-windows-10).
##### Docker
Check out https://www.docker.com/ to get docker for Windows. 

#### Mac and Linux
##### Jar
You'll just need to add the Java classpath to your bash_profile.
##### Docker
Check out https://www.docker.com/ to get docker for Mac and Linux.

### Properties
#### Plex
You need to find your plex movie all URL. Plex has a great write up on to get the URL. But first you'll need to get your token. The write ups are below.

https://support.plex.tv/articles/204059436-finding-an-authentication-token-x-plex-token/
https://support.plex.tv/articles/201638786-plex-media-server-url-commands/

It will look something like this.

http://127.0.0.1:32400/library/sections/1/all/?X-Plex-Token={My-Plex-Token}

Gaps supports a single movie URL or multiple. You can adjust to your needs. Then you need to go make an account on https://www.themoviedb.org and generate an API Key. Add that key to the application.yaml file under the movieDbApiKey property.

#### Folder
You need to list out the folders you want to read from. Review the file types for video and make sure every type you have is covered. Make sure you enable recursive search if needed. If your files aren't in the format as specified below, be sure to update the regex to match your file name structure.

 ```yaml
gaps:
  #Put your Plex Movie All URLs here
  #Gaps supports a single movie URL or multiple.
  #Single
  #movieUrls:
  #  - http://127.0.0.1:32400/library/sections/1/all/?X-Plex-Token={My-Plex-Token}

  #Go to https://www.themoviedb.org and make an API Key, place that key here
  #movieDbApiKey: {key}
  movieDbApiKey:

  # Optional
  # Go to https://www.themoviedb.org and make a custom list for GAPS https://www.themoviedb.org/list/<id number>
  # enter the <id number> below if you want GAPS to populate the list
  movieDbListId:

  #Should Gaps write out to a file as well as console
  writeToFile: true


  #######################################
  #
  #Can read from a folder, Plex, or both
  #At least, one of the two property groups needs to be filled in though
  #
  #######################################

  #List of folders where movies can be pulled from
  folder:
    #Required
    searchFromFolder: true

    #Required if searching from folders
    #List of folders to search in
    folders:
      - /Users/jhouse/Movies

    #Required if searching from folders
    #If the subfolder should be searched in
    recursive: true

    #Required if searching from folders
    #Add any movie formats as needed
    movieFormats:
      - avi
      - mkv
      - mp4
      - mov
      - webm
      - vob
      - flv
      - mts
      - m2ts
      - m4p
      - m4v
      - 3gp
      - 3g2

    #Movie Regex
    #Expected format is Movie Name (Year)
    #Example: The Fast and the Furious (2001)
    yearRegex: \(([1-2][0-9][0-9][0-9])\)

  #Plex connection timeouts when querying for all movies in the Plex section. Time is in seconds. Default is 180 seconds
  plex:
    searchFromPlex: false
    connectTimeout: 180
    writeTimeout: 180
    readTimeout: 180

    #Mulitple
    #movieUrls:
    #  - http://127.0.0.1:32400/library/sections/1/all/?X-Plex-Token={My-Plex-Token}
    #  - http://127.0.0.1:32400/library/sections/2/all/?X-Plex-Token={My-Plex-Token}
    #  - http://127.0.0.1:32400/library/sections/3/all/?X-Plex-Token={My-Plex-Token}
    movieUrls:
```

##### movieDbListId: \<list id>  
https://www.themoviedb.org/list/<list_id>  
https://www.themoviedb.org/list/123456
 
This property will populate a list for you in your TheMovieDB account. This list can then be imported into radarr using the "add from list" feature, or other media managment software.

**_**Enabling this feature will require user input in the terminal.**_**

### Running
#### Jar

You can run it with the run.bat or run.sh scripts. If you want to run command line you can run with. 
```bash
java -jar Gaps.{version-number}.jar
```
The output will come either to a file or the console. By default the output comes to a file. Gaps will print out information about duplicate movies and movies not found in the terminal. The program takes time but in the end you will get a list of movies missing from collections you have. If you have any questions, hit me up. Feel free to put up PRs to improve this as well.

#### Docker

First, the container must be built:

```bash
docker build -t gaps .
```

Next, run the container with the environment variables:

```bash
docker run -t -e DB_API_KEY= -e $PLEX_ADDRESS= -e $WRITE_TO_FILE= [-e TMDBLISTID= ] gaps  
```

In example:

```bash
docker run -t -e DB_API_KEY=myapikey -e $PLEX_ADDRESS=http://192.168.0.10:32400/library/sections/1/all/?X-Plex-Token=plextoken -e $WRITE_TO_FILE=true [-e TMDBLISTID=id] gaps 

Multiple URL:
docker run -t -e DB_API_KEY=myapikey -e $PLEX_ADDRESS=http://192.168.0.10:32400/library/sections/1/all/?X-Plex-Token=plextoken,http://192.168.0.10:32400/library/sections/2/all/?X-Plex-Token=plextoken  -e $WRITE_TO_FILE=true gaps
```

### Option Properties in Docker

CONNECT_TIMEOUT

WRITE_TIMEOUT

READ_TIMEOUT 

These are optional properties to help with Plex Sections that are very large. Timeouts can be set longer to help when parsing the big XML returned by Plex. They are not required and will default to 180 seconds.

```bash
docker run -t -e DB_API_KEY=myapikey -e PLEX_ADDRESS=http://192.168.0.10:32400/library/sections/1/all/?X-Plex-Token=plextoken -e WRITE_TO_FILE=true -e CONNECT_TIMEOUT=180 -e WRITE_TIMEOUT=180 -e READ_TIMEOUT=180 gaps
```


## License
Copyright 2019 Jason H House

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
