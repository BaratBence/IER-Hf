// Agent chef in project Restaurant

/* Initial beliefs and rules */

/* Initial goals */


/* Plans */

+stateMachine(Size,M1,M2,X,TX,Check,Prepare,MoveTo,Chopped,Serve,Making): Size>0 & Check & not Prepare & not MoveTo & not Chopped & not Serve & not Making & X=6<-checkResources.
+stateMachine(Size,M1,M2,X,TX,Check,Prepare,MoveTo,Chopped,Serve,Making): not Check & Prepare & not MoveTo & not Chopped & not Serve & not Making<-prepare.
+stateMachine(Size,M1,M2,X,TX,Check,Prepare,MoveTo,Chopped,Serve,Making): not Check & not Prepare & MoveTo & not Chopped &not Serve & not TX=X & not Making<-moveTo.
+stateMachine(Size,M1,M2,X,TX,Check,Prepare,MoveTo,Chopped,Serve,Making): not Check & not Prepare & not MoveTo & not Chopped & not Serve & TX<5 & not Making<-pickUp.
+stateMachine(Size,M1,M2,X,TX,Check,Prepare,MoveTo,Chopped,Serve,Making): not Check & not Prepare & not MoveTo & not Chopped & not Serve & TX>=5 & not Making<-putin.
+stateMachine(Size,M1,M2,X,TX,Check,Prepare,MoveTo,Chopped,Serve,Making): Chopped<-chopping.
+stateMachine(Size,M1,M2,X,TX,Check,Prepare,MoveTo,Chopped,Serve,Making): not Check & not Prepare & not MoveTo & not Chopped & not Serve & Making <-making.
+stateMachine(Size,M1,M2,X,TX,Check,Prepare,MoveTo,Chopped,Serve,Making): not Check & not Prepare & not MoveTo & not Chopped & Serve & not Making <-serve.

