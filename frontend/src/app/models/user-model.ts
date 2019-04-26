import {RoleModel} from "./role-model";

export class UserModel {
  id: string;
  email: string;
  login: string;
  password: string;
  firstName: string;
  secondName: string;
  mobileNumber: string;
  logoUrl: string;
  birthDate: Date;
  role: RoleModel;

  static cloneBase(user: UserModel):UserModel{
    const cloneUserModel: UserModel = new UserModel();
    cloneUserModel.id = user.id;
    cloneUserModel.email = user.email;
    cloneUserModel.login = user.login;
    cloneUserModel.password = user.password;
    cloneUserModel.logoUrl = user.logoUrl;
    cloneUserModel.firstName = user.firstName;
    cloneUserModel.secondName = user.secondName;
    cloneUserModel.mobileNumber = user.mobileNumber;
    cloneUserModel.birthDate = user.birthDate;
    cloneUserModel.role = user.role;
    return cloneUserModel;
  }
}