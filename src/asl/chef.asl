// Agent chef in project Restaurant

/* Initial beliefs and rules */

/* Initial goals */


/* Plans */

//+checkResources(S,C) : S > 0  & C  <- checkResources.
+stateMachine(M1,M2,X,TX,C,P,M,S,MA): M1=Free & C & not P & not M & not S & not MA<-checkResources.
+stateMachine(M1,M2,X,TX,C,P,M,S,MA): M1=Free & not C & P & not M & not S & not MA<-prepare.
//+prepare(P) : P <- prepare.
+stateMachine(M1,M2,X,TX,C,P,M,S,MA): M1=Free & not C & not P & M & not S & not TX=X & not MA<-moveTo.
//+moveTo(M,TX,X) : M & not TX=X <- moveTo.
+stateMachine(M1,M2,X,TX,C,P,M,S,MA): M1=Free & not C & not P & not M & not S & TX<5 & not MA<-pickUp.
+stateMachine(M1,M2,X,TX,C,P,M,S,MA): M1=Free & not C & not P & not M & not S & TX>=5 & not MA<-putin.
//+moveTo(M,TX,X) : not M & TX=X & not X=6 & not X=5 <-pickUp.
//+putin(X,C,M): X>4 & C>0 & not M<-putin.
+stateMachine(M1,M2,X,TX,C,P,M,S,MA): M1=Free & not C & not P & not M & not S & X=5 & MA<-baking.
+stateMachine(M1,M2,X,TX,C,P,M,S,MA): M1=Free & not C & not P & not M & not S & X=6 & MA<-cooking.
//+cooking(X,W): X=6 & W <-cooking.
//+baking(X,W): X=5 & W <-baking.
+stateMachine(M1,M2,X,TX,C,P,M,S,MA): M1=Ready | M2=Ready & not C & not P & not M & S & X=6 & not MA<-serve.
//+serve(M1,W,G,M): M1=Ready & W & not G & not M <-serve.
