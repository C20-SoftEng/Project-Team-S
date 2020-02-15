using System;
using System.Collections.Generic;
using System.Text;

namespace SquishySquidward.ReloadedAndMobilized
{
    public enum ServiceKind
    {
        Janitor
    }
    public enum ServiceStatus
    {
        Created,
        Assigned, 
        Completed
    }

    public class ServiceRequest
    {
        public ServiceKind Kind { get; }
        public Employee Employee { get; }
        public ServiceStatus Status => throw new NotImplementedException();

    }
}
