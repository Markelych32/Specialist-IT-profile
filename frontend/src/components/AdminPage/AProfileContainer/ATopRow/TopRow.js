import React from "react";
import styled from "styled-components";
import GeneralContainer from "./GeneralContainer/GeneralContainer.js";
import HardContainer from "./AHardContainer/AHardContainer.js";

const TopRow = ({ userData, specificationsData }) => {
  return (
    <TopRowBlock>
      <GeneralContainer userData={userData} />
      <HardContainer specificationsData={specificationsData} />
    </TopRowBlock>
  );
};

const TopRowBlock = styled.div`
  display: flex;
  margin-bottom: 30px;
  @media (max-width: 1500px) {
    flex-direction: column;
    margin-bottom: 10px;
  }
`;

export default TopRow;
