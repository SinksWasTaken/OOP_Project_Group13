import java.time.LocalDate;

public class EmployeeClass 
{
    //Profile Info
    String username;
    String name;
    String surname;
    LocalDate dateOfBirth;
    LocalDate dateOfStart;
    String role;
    int roleIndex;
    //Non-Profile Info
    String email;
    String password;
    int phoneNo;

    public void setRole()
    {
        switch(roleIndex)
        {
            case 0 ->
            {
                role = "Manager";
            }
            case 1 ->
            {
                role = "Technician";
            }
            case 2 ->
            {
                role = "Engineer";
            }
            case 3 ->
            {
                role = "Intern";
            }
        }
    }

    public void printEmployee()
    {
        System.out.println("username: "+ username);
        System.out.println("name: "+ name);
        System.out.println("surname: "+ surname);
        System.out.println("dateOfBirth: "+ dateOfBirth.toString());
        System.out.println("dateOfStart: "+ dateOfStart.toString());
        System.out.println("role: "+ role);
        System.out.println("email: "+ email);
        System.out.println("password: "+ password);
        System.out.println("phoneNo: "+ phoneNo);

    }

}
