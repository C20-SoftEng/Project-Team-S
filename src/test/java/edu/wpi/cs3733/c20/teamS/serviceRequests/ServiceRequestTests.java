package edu.wpi.cs3733.c20.teamS.serviceRequests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceRequestTests {
    private static class MockRequest
    public static class NewRequest {
        private final ServiceRequest request = new ServiceRequest(69, ServiceKind.JANITOR);

        @Test
        public void hasStatus_created() {
            assertEquals(ServiceStatus.CREATED, request.status());
        }

        @Test
        public void hasAssignee_null() {
            assertNull(request.assignee());
        }
    }

    public static class AssignedRequest {
        private final Employee employee;
        private final ServiceRequest request;

        public AssignedRequest() {
            employee = new Employee(420, "Alice");
            request = new ServiceRequest(69, ServiceKind.JANITOR);
            request.assignTo(employee);
        }

        @Test
        public void hasStatus_Assigned() {
            assertEquals(ServiceStatus.ASSIGNED, request.status());
        }

        @Test
        public void hasAssignedEmployee() {
            assertEquals(employee, request.assignee());
        }
    }

    public static class CompletedRequest {
        private final Employee employee;
        private final ServiceRequest request;

        public CompletedRequest() {
            employee = new Employee(69, "Bob");
            request = new ServiceRequest(123, ServiceKind.JANITOR);
            request.assignTo(employee);
            request.complete();
        }

        @Test
        public void hasStatus_Completed() {
            assertEquals(ServiceStatus.COMPLETED, request.status());
        }

        @Test
        public void hasAssignedEmployeeAtCompletionTime() {
            assertEquals(employee, request.assignee());
        }
    }

    public static class CanceledRequest {
        private final ServiceRequest request;

        public CanceledRequest() {
            request = new ServiceRequest(47, ServiceKind.JANITOR);
            request.assignTo(new Employee(42, "Amy"));
            request.cancel();
        }

        @Test
        public void hasStatus_Canceled() {
            assertEquals(ServiceStatus.CANCELED, request.status());
        }

        @Test
        public void hasNullAssignee() {
            assertNull(request.assignee());
        }
    }
}
