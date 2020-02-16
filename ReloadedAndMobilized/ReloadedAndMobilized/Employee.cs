using System;
using System.Collections.Generic;
using System.Text;

namespace SquishySquidward.ReloadedAndMobilized
{
    public enum AccessLevel
    {
        User = 0,
        Employee = 1,
        Admin = 2
    }

    public class Employee
    {
        public Employee() { }
        public Employee(int id)
        {
            this.ID = id;
        }

        public int? ID { get; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string FullName => $"{FirstName} {LastName}";
        public AccessLevel AccessLevel { get; } = AccessLevel.Employee;
        public override string ToString() => FullName;
    }
}
