# EventManager
### TP de TACS 2C 2018
[![Build Status](https://travis-ci.org/alan07sl/EventManager.svg?branch=master)](https://travis-ci.org/alan07sl/EventManager)
[![Coverage Status](https://coveralls.io/repos/github/alan07sl/EventManager/badge.svg)](https://coveralls.io/github/alan07sl/EventManager)

## Uso
#### Para usar este software:

Usar la aplicación basada en spring boot que esta corriendo en Heroku, aplicación que expone una **API REST** (Documentada dentro de la aplicación con Swagger, por favor chequee debajo).

### Por favor chequee la [Documentación de la API](http://tacs-event-manager.herokuapp.com/swagger-ui.html)
Hecha para proveer documentación out of the box respecto de la API.

### Unit testing
- Los **Tests unitarios** fueron hechos bajo las mejores prácticas, como utilizar assertThat de modo que al fallar, los mensajes sean más explicitos, usando mocks para dependencias y web requests.
- **JaCoCo** chequea si la cantidad de **líneas de código cubiertas** esta por encima del **75 porciento**
- **JaCoCo** chequea que todas las clases excepto Main están siendo probadas.

### Calidad de código
- **CI** esta integrado con **JaCoCo** y **Sonar Cloud** para asegurar la calidad del código.
- El proyecto en **Sonar Cloud** puede ser accedido desde la siguiente **[URL](https://sonarcloud.io/organizations/tacs-utn/projects)**

### Arrancando el proyecto
- Para arrancar el proyecto correr el comando `mvn spring-boot:run` 

### [Link al Bot de Telegram](https://web.telegram.org/#/im?p=@EventM_bot)