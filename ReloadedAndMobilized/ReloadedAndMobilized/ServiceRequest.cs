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
        Completed,
        Canceled
    }

    public class ServiceRequest
    {
        public ServiceRequest(ServiceKind kind) : this(kind, DateTime.UtcNow) { }
        public ServiceRequest(ServiceKind kind, DateTime dateCreated)
        {
            this.Kind = kind;
            this.DateCreated = dateCreated;
            this.Status = ServiceStatus.Created;
        }
        public int? ID { get; }
        public DateTime DateCreated { get; }
        public ServiceKind Kind { get; }
        public Employee Employee { get; private set; }
        public ServiceStatus Status { get; private set; }
        public void Assign(Employee employee)
        {
            if (employee is null) throw new ArgumentNullException(nameof(employee));

            Employee = employee;
            Status = ServiceStatus.Assigned;
        }
        public void Complete()
        {
            Status = ServiceStatus.Completed;
        }
        public void Cancel()
        {
            Status = ServiceStatus.Canceled;
            Employee = null;
        }
    }
}
