#import "MailToPlugin.h"


@implementation MailToPlugin

@synthesize callbackIds = _callbackIds;

- (NSString*)callbackIds {
	return _callbackIds;
}

- (void) mailTo:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options
{	
	self.callbackIds = [arguments pop];
	
	NSArray *emails = [options objectForKey:@"emails"];
	NSArray *ccemails = [options objectForKey:@"ccemails"];
	NSArray *bccemails = [options objectForKey:@"bccemails"];	
	NSString* subject = [options valueForKey:@"subject"];
	NSString* body = [options valueForKey:@"message"];
	NSString* isHTML = [options valueForKey:@"isHtml"];
	NSArray *attachments = [options objectForKey:@"attachments"];
	
    MFMailComposeViewController *picker = [[MFMailComposeViewController alloc] init];
    picker.mailComposeDelegate = self;
    
	if(subject != nil)
		[picker setSubject:subject];

	if(body != nil)
	{
		if(isHTML != nil && [isHTML isEqualToString:@"yes"])
		{
			[picker setMessageBody:body isHTML:YES];
		}
		else
		{
			[picker setMessageBody:body isHTML:NO];
		}
	}
	
	if(emails != nil)
	{
		[picker setToRecipients:emails];
	}
	if(ccemails != nil)
	{
		[picker setCcRecipients:ccemails]; 
	}
	if(bccemails != nil)
	{
		[picker setBccRecipients:bccemails];
	}
	
	if (attachments != nil)
	{
		for (NSString *path in attachments) {
			BOOL fileExists = [[NSFileManager defaultManager] fileExistsAtPath:path];
			if (fileExists){
				NSString* fullPath = [path stringByExpandingTildeInPath];
				NSURL* fileUrl = [NSURL fileURLWithPath:fullPath];
				NSURLRequest* fileUrlRequest = [[NSURLRequest alloc] initWithURL:fileUrl cachePolicy:NSURLCacheStorageNotAllowed timeoutInterval:.1];
				
				NSError* error = nil;
				NSURLResponse* response = nil;
				NSData* fileData = [NSURLConnection sendSynchronousRequest:fileUrlRequest returningResponse:&response error:&error];
				NSString* mimeType = [response MIMEType];
				NSString* theFileName = [[fullPath lastPathComponent] stringByDeletingPathExtension];
				[picker addAttachmentData:fileData mimeType:mimeType fileName:theFileName];
			}
		}
	}

    if (picker != nil) {  	
        [self.viewController presentModalViewController:picker animated:YES];
    }
    [picker release];
}


- (void)mailComposeController:(MFMailComposeViewController*)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError*)error 
{   
	if (error != nil)
	{
		NSMutableDictionary *results = [NSMutableDictionary dictionary];
		[results setValue:[NSString stringWithFormat:@"%@", error] forKey:@"error"];
		CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:results];
		[self writeJavascript:[pluginResult toErrorCallbackString:self.callbackIds ]];	
	}
	else 
	{
		int webviewResult = 0;
	
    	switch (result)
    	{
        	case MFMailComposeResultCancelled:
				webviewResult = 0;
            break;
        	case MFMailComposeResultSaved:
				webviewResult = 1;
            break;
        	case MFMailComposeResultSent:
				webviewResult =2;
            break;
        	case MFMailComposeResultFailed:
            	webviewResult = 3;
            break;
        	default:
				webviewResult = 4;
            break;
    	}

    	[self.viewController dismissModalViewControllerAnimated:YES];	
		CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
		[self writeJavascript:[pluginResult toSuccessCallbackString:self.callbackIds ]];
	}
	
}

@end