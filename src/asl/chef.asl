// Agent chef in project Restaurant

/* Initial beliefs and rules */

/* Initial goals */


/* Plans */

+checkResources(S,X,Y) : S > 0  & X = 6 & Y = 2  <- checkResources.
+prepare(W,M,C) : W & not M & C==0 <- prepare.
+moveTo(M,TX,X) : M & not TX=X <- moveTo.
+moveTo(M,TX,X) : not M & TX=X <-pickUp.
