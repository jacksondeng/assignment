# Project Title
randomuser.me query library

## Getting Started

Using the source code instead of the aar file <br />
i) Add the activity that requires Gemaltoapi dependency into ActivityBindingModule.<br />
ii) Inject GemaltoApi into the activity.<br />

public Methods : <br />
i)   GemaltoApi.getUsers(String gender,String seed,String number) <br />
ii)  GemaltoApi.listStoredUsers() <br />
iii) GemaltoAPi.storeUser(User user) <br />
iv)  GemaltoApi.deleteUser(User user)  <br />
All the methods above return void and will only update UserRepository's Livedata in GemaltoApi class. <br />
Observe the Livedata localUsers,remoteUsers to update UI.  <br />


<br />
Using aar file <br />
i) get GemaltoApi object from application class.<br />
ii) use it in activity/fragment.<br />

### Prerequisites

  targetCompatibility 1.8
  sourceCompatibility 1.8

## Authors

**Jackson Deng**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

