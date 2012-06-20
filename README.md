# MailTo plugin for Cordova

This is a prototype of a mail-to plugin for android and ios

The goal is for a single JavaScript file to be usable on all supported
platforms, and the native code to be installed in a project through a [separate
script](http://github.com/alunny/pluginstall) (to install on iOS, 
you will need pluginstall version 0.3.3 or above)

## The Structure

    plugin.xml
    -- src
      -- android
        -- MailToPlugin.java
    -- ios
        -- MailToPlugin.h
        -- MailToPlugin.m
    -- www
      -- mailto.js

## plugin.xml

The plugin.xml file is loosely based on the W3C's Widget Config spec.

It is in XML to facilitate transfer of nodes from this cross platform manifest
to native XML manifests (AndroidManifest.xml, App-Info.plist, config.xml (BB)).

## MailTo JavaScript API

As with most Cordova/PhoneGap APIs, functionality is not available until the
`deviceready` event has fired on the document. The `mailto.js` file
should be included _after_ the `phonegap.js` file.

Call the plugin, specifying subject, text, success function, and failure function


	window.plugins.MailTo.mailTo({
    	subject: 'Subject',
    	message: '<b>message</b>',
    	isHtml : true,
    	emails : ["mail@gmail.com"],
    	ccemails : ["ccmail@gmail.com"],
    	bccemails : ["bccmail@gmail.com"],
    	attachments : ["/sdcard/downloads/test.html"],
    	chtitle : "Mail To:" //Android spec
    	},
    	function() {}, // Success function
    	function() {alert('Mailto failed')} // Failure function
	);

## License

Apache