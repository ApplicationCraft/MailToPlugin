
(function(gap) {	
	var MailTo = function() {};
	            
	MailTo.prototype.mailTo = function(content, success, fail) {
		if (!content){
			if (typeof success == "function")
	    		fail("Nothing to send");
			return;
		}
		if (typeof content.emails == "string"){
			content.emails = [content.emails];
		}
		if (typeof content.ccemails == "string"){
			content.ccemails = [content.ccemails];
		}
		if (typeof content.bccemails == "string"){
			content.bccemails = [content.bccemails];
		}
		
		content.isHtml = (content.isHtml) ? "yes" : "no";
		
	    return gap.exec( function(args) {
	    	if (typeof success == "function")
	    		success(args);
	    }, function(args) {
	    	if (typeof success == "function")
	    		fail(args);
	    }, 'MailToPlugin', 'mailTo', [content]);
	};

	gap.addConstructor(function(){
		gap.addPlugin('MailTo', new MailTo());
	});

}).call(this, window.Cordova || window.PhoneGap || window.cordova);