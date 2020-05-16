// Agent chef in project Restaurant

/* Initial beliefs and rules */

/* Initial goals */


/* Plans */

+checkResources(S,X,Y,W) : S > 0  & X = 6 & Y = 2 & not W <- checkResources.
+prepare(W,M,C,G) : W & not M & C==0 & not G <- prepare.
+moveTo(M,TX,X) : M & not TX=X <- moveTo.
+moveTo(M,TX,X) : not M & TX=X & not X=6 & not X=5 <-pickUp.
+putin(X,C,M): X>4 & C>0 & not M<-putin.
+cooking(X,W): X=6 & W <-cooking.
+baking(X,W): X=5 & W <-baking.
+serve(M1): M1=Ready <-serve.
