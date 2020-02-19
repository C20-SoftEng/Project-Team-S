package edu.wpi.cs3733.c20.teamS.serviceRequests;

public class InterpreterServiceRequest extends ServiceRequest {
    private final String implementedBy = "Long live Kobe!";
    private String interpreterType_;

    public InterpreterServiceRequest() {
        this(null);
    }
    public InterpreterServiceRequest(Integer id) {
        super(id);
    }

    @Override
    public void accept(ServiceVisitor visitor) {
        visitor.visit(this);
    }

    public String implementedBy() {return implementedBy; }
    public String interpreterType() { return interpreterType_; }

    public void setInterpreterType_(String interpreter){
        interpreterType_ = interpreter;
    }
}
