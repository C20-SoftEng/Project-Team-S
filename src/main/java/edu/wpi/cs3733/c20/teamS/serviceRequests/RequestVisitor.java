package edu.wpi.cs3733.c20.teamS.serviceRequests;

/**
 * Implements the Visitor Pattern for ServiceRequests. Talk to Newell if you have questions
 * about the visitor pattern.
 *
 * If you create a class that derives from ServiceRequest, you must also add an abstract overload
 * of visit() to RequestVisitor that takes a request of your type.
 */
public abstract class RequestVisitor {
    public abstract void visit(JanitorRequest request);
    public abstract void visit(HazmatRequest request);
}
