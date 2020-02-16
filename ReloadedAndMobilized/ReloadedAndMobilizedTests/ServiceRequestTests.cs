using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Text;

namespace SquishySquidward.ReloadedAndMobilized
{

    public class ServiceRequestTests
    {
        [TestFixture]
        public class NewRequest
        {
            private static readonly ServiceKind kind = ServiceKind.Janitor;
            private readonly ServiceRequest request = new ServiceRequest(kind);

            [Test]
            public void HasNoAssignedEmployee()
            {
                Assert.That(request.Employee, Is.Null);
            }

            [Test]
            public void HasStatusCreated()
            {
                Assert.That(request.Status, Is.EqualTo(ServiceStatus.Created));
            }

            [Test]
            public void HasSpecifiedServiceKind()
            {
                Assert.That(request.Kind, Is.EqualTo(ServiceKind.Janitor));
            }

            [Test]
            public void ThrowsWhenAssignedToNullEmployee()
            {
                Assert.Throws<ArgumentNullException>(() => request.Assign(null));
            }
        }

        [TestFixture]
        public class AssignedRequest
        {
            private ServiceRequest request;
            private Employee bob;

            [SetUp]
            public void SetUp()
            {
                request = new ServiceRequest(ServiceKind.Janitor);
                bob = new Employee { FirstName = "Bob" };
                request.Assign(bob);
            }

            [Test]
            public void HasAssignedEmployee()
            {
                Assert.That(request.Employee, Is.EqualTo(bob));
            }

            [Test]
            public void HasStatusAssigned()
            {
                Assert.That(request.Status, Is.EqualTo(ServiceStatus.Assigned));
            }
        }

        [TestFixture]
        public class CompletedRequest
        {
            private ServiceRequest request;
            private Employee alice;

            [SetUp]
            public void SetUp()
            {
                request = new ServiceRequest(ServiceKind.Janitor);
                alice = new Employee { FirstName = "Alice" };
                request.Assign(alice);
                request.Complete();
            }

            [Test]
            public void HasStatusCompleted()
            {
                Assert.That(request.Status, Is.EqualTo(ServiceStatus.Completed));
            }

            [Test]
            public void HasAssignedEmployee()
            {
                Assert.That(request.Employee, Is.EqualTo(alice));
            }
        }

        [TestFixture]
        public class CanceledRequest
        {
            private ServiceRequest request;
            
            [SetUp]
            public void SetUp()
            {
                request = new ServiceRequest(ServiceKind.Janitor);
                request.Assign(new Employee());
                request.Cancel();
            }

            [Test]
            public void HasStatusCanceled()
            {
                Assert.That(request.Status, Is.EqualTo(ServiceStatus.Canceled));
            }

            [Test]
            public void HasNullAssignedEmployee()
            {
                Assert.That(request.Employee, Is.Null);
            }
        }
    }
}
