# Oracle Micros Simphony replica

This project tries to imitate the Oracle's Point of Sale software for restaurants [Micros Simphony](https://www.oracle.com/food-beverage/restaurant-pos-systems/simphony-pos/) as a RESTful web service.

## Getting Started

These instructions will give you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on deploying the project on a live system.

### About the application

This web service follows the Representational State Transfer principles, uses Maven as the build tool and the current LTS version of Java, 17.
I hope to add more functionality to this application in the future but for now this project uses the following dependencies:

- Spring MVC
- Spring Web
- Spring Data JPA
- Mockito
- MySQL Database

### Running the application

You can run this application from your favorite IDE or by running the following command:

    ./mvnw spring-boot:run

## Future features
These are some features that I plan to add to the project so that it scales little by little and eventually becomes a robust one:
- [ ] User logic and hierarchy with different permissions
- [ ] Robust payment authentication
- [ ] Cool frontend

And more...

## Contributing

Feel free to make suggestions for this project, pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Authors

  - **Tomás Iglesias** -
    [IglesiasT](https://github.com/IglesiasT)

See also the list of
[contributors](https://github.com/IglesiasT/spring-students-management/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE) file for details.
