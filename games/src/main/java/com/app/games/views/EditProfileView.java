package com.app.games.views;  

import com.app.games.model.User;  
import com.app.games.service.UserService;  
import com.vaadin.flow.component.button.Button;  
import com.vaadin.flow.component.html.H3;  
import com.vaadin.flow.component.notification.Notification;  
import com.vaadin.flow.component.textfield.TextField;  
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;  
import com.vaadin.flow.component.orderedlayout.VerticalLayout;  
import com.vaadin.flow.router.BeforeEnterEvent;  
import com.vaadin.flow.router.BeforeEnterObserver;  
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import org.springframework.beans.factory.annotation.Autowired;  

@Route("edit-profile")  
public class EditProfileView extends VerticalLayout implements BeforeEnterObserver {  

    private final UserService userService;  
    private TextField nombreField;  
    private TextField apellidoField;  
    private TextField correoField;  
    private TextField nombreUsuarioField;  
    private TextField contrasenaField;  
    private String userId;  

    @Autowired  
    public EditProfileView(UserService userService) {  
        this.userService = userService;  

        setPadding(false); // Eliminar el padding  
        setMargin(false); // Eliminar el margin  
        setWidthFull();  

        H3 title = new H3("Editar Perfil");  
        title.getStyle().set("margin-left", "20px");  
        add(title);  

        nombreField = new TextField("Nombre");  
        apellidoField = new TextField("Apellido");  
        correoField = new TextField("Correo");  
        nombreUsuarioField = new TextField("Nombre de Usuario");  
        contrasenaField = new TextField("Contraseña");  

        Button backButton = new Button("Regresar");  
        backButton.getStyle().set("margin-left", "20px");  

        Button saveButton = new Button("Guardar Cambios");  
        saveButton.getStyle().set("margin-left", "20px");  

        VerticalLayout layout = new VerticalLayout(  
                createRow("Nombre", nombreField),  
                createRow("Apellido", apellidoField),  
                createRow("Correo", correoField),  
                createRow("Nombre de Usuario", nombreUsuarioField),  
                createRow("Contraseña", contrasenaField)  
        );  
        layout.getStyle().set("margin-left", "20px");  

        // Crear un layout para los botones  
        VerticalLayout buttonLayout = new VerticalLayout(saveButton, backButton);  
        buttonLayout.getStyle().set("margin-left", "20px").set("margin-bottom", "20px");  

        // Añadir todos los layouts al componente principal  
        add(layout, buttonLayout);  

        // Añadir listener para el botón de guardar  
        saveButton.addClickListener(e -> saveUserProfile());  

        // Añadir listener para el botón de regresar  
        backButton.addClickListener(e -> navigateBack());  
    }  

    private void navigateBack() {  
        // Redirige a la vista anterior, ajusta la ruta según sea necesario  
        getUI().ifPresent(ui -> ui.navigate("/my-profile"));  
    }  

    private HorizontalLayout createRow(String labelText, TextField textField) {  
        textField.setLabel(labelText);  
        return new HorizontalLayout(textField);  
    }  

    @Override  
    public void beforeEnter(BeforeEnterEvent event) {  
        // Obtiene el userId de la sesión actual  
        this.userId = (String) VaadinSession.getCurrent().getAttribute("userId");     

        // Si no se encuentra el usuario, redirigir al login  
        if (this.userId != null) {  
            User user = userService.findUserById(this.userId); // Obtiene el usuario por ID  
            if (user != null) {  
                populateForm(user); // Llama a populateForm con el objeto User  
            } else {  
                Notification.show("No se encontró el usuario. Por favor, inicia sesión.", 3000, Notification.Position.MIDDLE);  
                event.rerouteTo("login");  
            }  
        } else {  
            Notification.show("No se encontró el usuario. Por favor, inicia sesión.", 3000, Notification.Position.MIDDLE);  
            event.rerouteTo("login");  
        }  
    }  

    private void populateForm(User user) {  
        nombreField.setValue(user.getNombre());  
        apellidoField.setValue(user.getApellido());  
        correoField.setValue(user.getCorreo());  
        nombreUsuarioField.setValue(user.getNombreUsuario());  
        contrasenaField.setValue(user.getContrasena());  
    }  

    private void saveUserProfile() {  
        User user = new User();  

        user.setNombre(nombreField.getValue());  
        user.setApellido(apellidoField.getValue());  
        user.setCorreo(correoField.getValue());  
        user.setNombreUsuario(nombreUsuarioField.getValue());  
        user.setContrasena(contrasenaField.getValue());  

        userService.updateUser(userId, user); // Asegúrate de modificar el método para recibir el userId  
        Notification.show("Perfil actualizado exitosamente!", 3000, Notification.Position.MIDDLE);  
        navigateBack(); // Regresar a la vista anterior  
    }  
}