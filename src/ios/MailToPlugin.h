#import <Foundation/Foundation.h>
#import <MessageUI/MFMailComposeViewController.h>
#ifdef CORDOVA_FRAMEWORK
#import <Cordova/CDVPlugin.h>
#else
#import "CDVPlugin.h"
#endif


@interface MailToPlugin : CDVPlugin < MFMailComposeViewControllerDelegate > {
	NSString* callbackIds;        
}

@property (nonatomic, retain) NSString *callbackIds;

- (void) mailTo:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;

@end