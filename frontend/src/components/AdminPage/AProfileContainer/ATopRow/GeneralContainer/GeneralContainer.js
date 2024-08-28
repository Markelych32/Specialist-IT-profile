import React from "react";
import styled from "styled-components";
import UnknownProfile from "../../../../assets/images/UnknownProfile.png";

const GeneralBlock = styled.div`
  background-color: #eef1f6;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  font-family: "Inter", sans-serif;
`;

const Avatar = styled.img`
  width: 220px;
  height: 220px;
  border-radius: 50%;
  border: 4px solid white;
  margin-bottom: 10px;
`;

const UserName = styled.h2`
  font-size: 24px;
  font-weight: 600;
  margin: 10px 0;
`;

const UserInfo = styled.p`
  font-size: 20px;
  font-weight: 400;
  margin: 5px 0;
  color: #333;
`;

const GeneralContainer = React.memo(({ userData }) => {
  const { avatar, name, birthDate, gender, location } = userData;

  return (
    <GeneralBlock>
      <Avatar src={UnknownProfile} alt="User Avatar" />
      <UserName>{name}</UserName>
      <UserInfo>{birthDate}</UserInfo>
      <UserInfo>{gender}</UserInfo>
      <UserInfo>{location}</UserInfo>
    </GeneralBlock>
  );
});

export default GeneralContainer;
