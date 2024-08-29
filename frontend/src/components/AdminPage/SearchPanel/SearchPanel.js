import React from "react";
import styled from "styled-components";

const SearchPanel = ({ value, onChange }) => {
  return (
    <SearchContainer>
      <SearchHeader>Поиск пользователей</SearchHeader>
      <SearchInput
        type="text"
        placeholder="Введите Фамилию или Имя пользователя"
        value={value}
        onChange={onChange}
      />
    </SearchContainer>
  );
};

export const SearchHeader = styled.div`
  color: #000000;
  font-family: "Inter", sans-serif;
  font-weight: 600;
  margin-bottom: 10px;
  font-size: 24px;
  text-align: center;
  width: 100%;
`;

const SearchContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  margin-bottom: 20px;
`;

const SearchInput = styled.input`
  width: 100%;
  max-width: 500px;
  padding: 10px 15px;
  font-family: "SansSerif", sans-serif;
  font-size: 16px;
  font-weight: 400;
  border: 1px solid #ffffff;
  border-radius: 8px;
  box-sizing: border-box;
  outline: none;
  background-color: #eef1f6;

  &:focus {
    border-color: #ffffff;
  }

  &::placeholder {
    color: #999;
  }
`;

export default SearchPanel;
