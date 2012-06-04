# MailTo plugin for Cordova

This is a prototype of a mail-to plugin for android

The goal is for a single JavaScript file to be usable on all supported
platforms, and the native code to be installed in a project through a [separate
script](http://github.com/alunny/pluginstall)

## The Structure

    plugin.xml
    -- src
      -- android
        -- MailToPlugin.java
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


	window.plugins.MailTo.show({
    	subject: 'Subject',
    	message: '<b>message</b>',
    	isHtml : true,
    	emails : ["mail@gmail.com"],
    	ccemails : ["ccmail@gmail.com"],
    	bccemails : ["bccmail@gmail.com"],
    	attachments : ["/sdcard/downloads/test.html"],
    	chtitle : "Mail To:"},
    	function() {}, // Success function
    	function() {alert('Mailto failed')} // Failure function
	);

## License

The MIT License

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
