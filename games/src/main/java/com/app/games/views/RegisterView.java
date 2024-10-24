package com.app.games.views;  

import com.vaadin.flow.component.button.Button;  
import com.vaadin.flow.component.notification.Notification;  
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;  
import com.vaadin.flow.component.textfield.PasswordField;  
import com.vaadin.flow.component.textfield.TextField;  
import com.vaadin.flow.component.html.H1;  
import com.vaadin.flow.router.Route;  
import com.app.games.model.RegisterRequest;  
import com.app.games.model.User;  
import com.app.games.service.UserService;  

@Route("register")  
public class RegisterView extends VerticalLayout {
    
    private HeaderComponent header;

    public RegisterView(UserService userService) {   

        setPadding(false); // Eliminar el padding  
        setMargin(false); // Eliminar el margin  
        setWidthFull();

        header = new HeaderComponent();  
        
        // Solicitando que el título no sea clickeable y ocultando el perfil  
        header.setTitleClickable(false);  
        header.setProfileLinkVisible(false);  

        // Agregar el header a la vista  
        add(header);

       H1 title = new H1("Register");  
        title.getStyle().set("margin-left", "20px"); // Ajustar margen izquierdo para el título  

        TextField nombre = new TextField("Name");  
        nombre.getStyle().set("margin-left", "20px"); // Ajustar margen izquierdo para el campo de nombre  

        TextField apellido = new TextField("LastName");  
        apellido.getStyle().set("margin-left", "20px"); // Ajustar margen izquierdo para el campo de apellido  

        TextField correo = new TextField("Email");  
        correo.getStyle().set("margin-left", "20px"); // Ajustar margen izquierdo para el campo de correo  

        TextField nombreUsuario = new TextField("Username");  
        nombreUsuario.getStyle().set("margin-left", "20px"); // Ajustar margen izquierdo para el campo de nombre de usuario  

        PasswordField contrasena = new PasswordField("Password");  
        contrasena.getStyle().set("margin-left", "20px"); // Ajustar margen izquierdo para el campo de contraseña  

        Button registerButton = new Button("Register");  
        Button clearButton = new Button("Clean");
        Button goBackButton = new Button("Go Back");   

        // Ajustar márgenes izquierdo para los botones también si es necesario  
        registerButton.getStyle().set("margin-left", "20px");  
        clearButton.getStyle().set("margin-left", "10px"); // Espacio inferior para el botón limpiar
        goBackButton.getStyle().set("margin-left", "10px"); 

        registerButton.addClickListener(event -> {  
            // Obtener los valores de los campos y crear el objeto RegisterRequest  
            RegisterRequest registerRequest = new RegisterRequest();  
            registerRequest.setNombre(nombre.getValue());  
            registerRequest.setApellido(apellido.getValue());  
            registerRequest.setCorreo(correo.getValue());  
            registerRequest.setNombreUsuario(nombreUsuario.getValue());  
            registerRequest.setContrasena(contrasena.getValue());  
            
            try {  
                User createdUser = userService.register(registerRequest);  
                Notification.show("User register succesfully: " + createdUser.getNombreUsuario());
                getUI().ifPresent(ui -> ui.navigate("/login"));    
            } catch (Exception e) {  
                Notification.show("Error: " + e.getMessage(), 3000, Notification.Position.MIDDLE);  
            }  
        });  

        clearButton.addClickListener(event -> {  
            nombre.clear();  
            apellido.clear();  
            correo.clear();  
            nombreUsuario.clear();  
            contrasena.clear();  
        });  

        goBackButton.addClickListener(event -> {  
            getUI().ifPresent(ui -> ui.navigate("login")); // Redirigir a la vista de inicio de sesión  
        });

        // Agrupar los campos de entrada en HorizontalLayouts  
        HorizontalLayout nombreApellidoLayout = new HorizontalLayout(nombre, apellido);  
        HorizontalLayout correoUsuarioLayout = new HorizontalLayout(correo, nombreUsuario);  
        
        // Agrupar los botones en un HorizontalLayout  
        HorizontalLayout buttonsLayout = new HorizontalLayout(registerButton, clearButton, goBackButton);  
        
        // Agregar todos los layouts en el layout principal  
        add(title, nombreApellidoLayout, correoUsuarioLayout, contrasena, buttonsLayout);  
    }   
 
}