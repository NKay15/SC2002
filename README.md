# SC2002

Hospital Management System Notes by MACS group 3

## TODO :

- [ ] Random Testing
- [ ]  UML Class Diagram
- [ ]  Final Report
- [ ]  Slides

### LYX
See todo.org

## Test
1. First-Time Login and Password Change
● User logs in with default password, changes it and set up 2FA.
● Verify that the password change is successful, and the user can log in with the
new password and 2FA.
2. Set Availability for Appointments (Doctor)
● Doctor sets or updates their availability for patient appointments.
● Verify that the doctor's availability is updated, and patients can see the new slots
when scheduling appointments.
3. View Available Appointment Slots (Patient)
● Patient views available appointment slots with doctors.
● Verify that the system displays a list of available appointment slots, showing
doctors' names, dates, and times.
4. Schedule an Appointment (Patient)
● Patient schedules a new appointment with a doctor.
● Verify that the appointment is scheduled successfully with status "confirmed".
The selected time slot becomes unavailable to other patients. The system should
prevent the patient from booking a time slot that is unavailable/already booked.
5. Accept or Decline Appointment Requests (Doctor)
● Doctor accepts or declines an appointment request from a patient.
● Verify that the appointment status changes to "confirmed" when accepted or
“cancelled” when declined , and the patient is able to see the updated status of
the appointment.
6. Reschedule an Appointment (Patient)
● Patient reschedules an existing appointment to a new slot.
● Verify that the appointment is rescheduled successfully. The previous time slot
becomes available, and the new slot is reserved.
7. Cancel an Appointment (Patient)
● Patient cancels an existing appointment.
● Verify that the appointment is canceled successfully, and the time slot becomes
available for others.
8. Update Personal Information (Patient)
● Patient updates their email address and contact number.
● Verify that patient's contact information is updated successfully, and the changes
are reflected in the medical record.
9. View Personal Schedule (Doctor)
● Doctor views their personal appointment schedule.
● Verify that the system displays the doctor's upcoming appointments and
availability slots.
10. View Scheduled Appointments (Patient)
● Patient views their list of scheduled appointments.
● Verify that the system displays all upcoming appointments with details like
doctor name, date, time, and status.
11. View Upcoming Appointments (Doctor)
● Doctor views all upcoming confirmed appointments.
● Verify that the system displays a list of all upcoming appointments with patient
details and appointment times.
12. Record Appointment Outcome (Doctor)
● Doctor records the outcome of a completed appointment.
● Verify that the appointment outcome is recorded, and relevant updates are visible
to the patient under “View Past Appointment Outcome Records”.
13. Update Patient Medical Records (Doctor)
● Doctor adds a new diagnosis and treatment plan to a patient's medical record.
● Verify that the medical record is updated successfully, reflecting the new information.
14. View Patient Medical Records (Doctor)
● Doctor views medical records of patients under their care.
● Verify that the patient's medical record is displayed, including all relevant
medical history.
15. View Medical Record (Patient)
● Patients view their own medical record.
● Verify that the system displays the patient's medical record, including Patient ID,
Name, Date of Birth, Gender, Contact Information, Blood Type, and Past
Diagnoses and Treatments.
16. View Past Appointment Outcome Records (Patient)
● Patient views outcome records of past appointments.
● Verify that the system displays past appointment details, including services
provided, prescribed medications, and consultation notes.
17. Update Prescription Status (Pharmacist)
● Pharmacist updates the status of a prescription to "dispensed."
● Verify that the prescription status is updated, and the change is reflected in the
patient's records.
18. View Appointment Outcome Record (Pharmacist)
● Pharmacist views appointment outcome records to process prescriptions.
● Verify that the system displays the appointment outcome details, including
prescribed medications.
19. View Medication Inventory (Pharmacist)
● Pharmacist views the current medication inventory.
● Verify that the system displays a list of medications, including stock levels.
20. View Medication Inventory (Pharmacist)
● Pharmacist views the current medication inventory.
● Verify that the system displays a list of medications, including stock levels.
21. Submit Replenishment Request (Pharmacist)
● Pharmacist submits a replenishment request for low-stock medications.
● Verify that the replenishment request is submitted successfully, pending approval
from the administrator.
22. View and Manage Hospital Staff (Admin)
● Administrators can view the list of hospital staff and add, update or remove staff
members.
● Verify that the displayed list of staff is updated with any changes.
23. View Appointments Details (Admin)
● Administrator views all appointments.
● Verify that the system displays a list of appointments including details like
Patient ID, Doctor ID, status, and date/time.
24. View and Manage Medication Inventory (Admin)
● Administrator updates the stock level of a medication.
● Verify that the medication's stock level is updated in the inventory.
25. Approve Replenishment Requests (Admin)
● Administrator approves a replenishment request from a pharmacist.
● Verify that the request status changes to "approved," and the medication
inventory is updated accordingly.
26. Login with Incorrect Credentials
● User attempts to log in with an incorrect password.
● Verify that the system displays an error message indicating invalid credentials,
and login is denied.



-------------
1. Patient Actions
Test Case 1: 
Test Case 2: 
Test Case 3: 
Test Case 4: 
Test Case 5: 
Test Case 6: 
Test Case 7: 
Test Case 8: 
SC/CE/CZ2002 Object-Oriented Design & Programming Assignment
2. Doctor Actions
Test Case 9: 
Test Case 10: 
Test Case 11:
Test Case 12: 
Test Case 13: 
Test Case 14: 
Test Case 15: 
3. Pharmacist Actions
Test Case 16: 
Test Case 17: 
SC/CE/CZ2002 Object-Oriented Design & Programming Assignment
Test Case 18: 
Test Case 19: 
4. Administrator Actions
Test Case 20: 
Test Case 21: 
Test Case 22: 
Test Case 23: 
5. Login System and Password Management
Test Case 25: First-Time Login and Password Change
● User logs in with default password and changes it.
● Verify that the password change is successful, and the user can log in with the
new password.
Test Case 26: 