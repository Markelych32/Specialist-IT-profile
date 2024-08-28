import React from "react";
import styled from "styled-components";

const Legend = styled.div`
  font-size: 18px;
  color: #000000;
  margin-top: 20px;
  margin-bottom: 20px;
  text-align: left;
  padding-left: 10px;
  align-items: center;
  gap: 10px;
  @media (max-width: 500px) {
    font-size: 15px;
  }
`;
const LegengHeader = styled.div`
  color: #000000;
  font-family: "Inter", sans-serif;
  font-weight: 600;
  margin-bottom: 10px;
`;
const LegendItem = styled.div`
  margin-bottom: 5px;
  font-family: "Inter", sans-serif;
  font-weight: 400;
  word-wrap: break-word;
  white-space: normal;
`;

const LegendInfo = ({ showCountLegend = 5 }) => {
  const items = [
    "③ - Экспертный уровень владения компетенцией",
    "② - Продвинутый уровень владения компетенцией",
    "① - Базовый уровень владения компетенцией",
    "⓪ - Компетенция не развита",
    "❗ - Негативное проявление компетенции",
  ];

  return (
    <Legend>
      <LegengHeader>Пояснение</LegengHeader>
      {items.slice(0, showCountLegend).map((item, index) => (
        <LegendItem key={index}>{item}</LegendItem>
      ))}
    </Legend>
  );
};

export default LegendInfo;
