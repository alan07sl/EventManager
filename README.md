# EventManager
### TP de TACS 2C 2018

## Uso
#### Para usar este software:

Usar la aplicación basada en spring boot que esta corriendo en Heroku, aplicación que expone una **API REST** (Documentada dentro de la aplicación con Swagger, por favor chequee debajo).

### Por favor chequee la [Documentación de la API](http://tacs-event-manager.herokuapp.com/swagger-ui.html)
Hecha para proveer documentación out of the box respecto de la API.

### La aplicación es gestionada y fue configurada para utilizar CI/CD Travis-Heroku
Para ver el **Estado actual de CI** por favor entre a la siguiente **[URL](https://travis-ci.org/alan07sl/event-manager)**

### Unit testing
- Los **Tests unitarios** fueron hechos bajo las mejores prácticas, como utilizar assertThat de modo que al fallar, los mensajes sean más explicitos, usando mocks para dependencias y web requests.
- **JaCoCo** chequea si la cantidad de **líneas de código cubiertas** esta por encima del **75 porciento**
- **JaCoCo** chequea que todas las clases excepto Main están siendo probadas.

### Calidad de código
- **CI** esta integrado con **JaCoCo** y **Sonar Cloud** para asegurar la calidad del código.
- El proyecto en **Sonar Cloud** puede ser accedido desde la siguiente **[URL](https://sonarcloud.io/organizations/tacs-utn/projects)**