# RMBridge binding for OpenHAB2
A RMBridge binding for OpenHAB2 for those impatient of broadlink-java-api

This is a quick and dirty binding for OpenHAB2.

## Adding a new remote/on-off switch

1. Create a ```.things``` file in ```/things```, according to your sitemap name. (e.g. ```myhome.sitemap``` with ```myhome.things```)

2. Put your configuration like this and replace ```<NAME>``` with the thing name, ```<HOSTNAME>``` with the RMBridge hostname/IP, ```<PORT>``` with the RMBridge running port (default: 7474), ```<TARGET-MAC>``` with the targeted Broadlink device to control, ```<ON-CODE>``` and ```<OFF-CODE>``` with the ON-OFF switch configuration

    ```
    rmbridge:remote-switch:<NAME> [ bridge-hostname="<HOSTNAME>", bridge-port=<PORT>, target-mac="<TARGET-MAC>", on-hex="<ON-CODE>", off-hex="<OFF-CODE>" ]
    ```
    
3. Create a ```.things``` file in ```/things```, according to your sitemap name. (e.g. ```myhome.sitemap``` with ```myhome.things```), and add the following, replace ```<NAME>``` with the Thing name specified in last step, ```TestIsATestLight``` and ```This is a test light``` can be replaced with desired switch name and friendly typed name.

    ```
    Switch ThisIsATestLight "This is a test light" { channel="rmbridge:remote-switch:<NAME>:channel-switch" }
    ```
    
4. Add the following to your sitemap frame, replace ```<NAME>``` with the Thing name specified in last step

    ```
    Switch item=<NAME>
    ```
    
5. Restart/Start OpenHAB 2 and try it out by using the sitemap.
    