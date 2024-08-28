import React, { useState, useCallback, useMemo } from "react";
import styled from "styled-components";
import getIconForMark from "../../../../../common/IconsForMarks/IconsForMarks";
import colorMap from "../../../../../common/ColorMap/ColorMap";
import T1_House from "../../../../../assets/images/T1_House.png";
import T1_Computer from "../../../../../assets/images/T1_Computer.png";
import {
  ErrorMessage,
  SuccessNotificationText,
  SuccessNotification,
  T1Computer,
  T1House,
  ImageOverlay,
  Select,
  SaveButton,
  CloseButton,
  ModalInputText,
  ModalInputTextHeader,
  ModalContent,
  ModalHeader,
  ModalOverlay,
} from "../../../../../common/ModalWindow/ModalWindow";

const TableWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  overflow-x: auto;
`;

const Table = styled.div`
  display: grid;
  grid-template-columns: repeat(5, minmax(210px, 1fr));
  gap: -1px;
  width: 100%;
  box-sizing: border-box;
  overflow-x: auto;
`;

const TableItem = styled.div`
  background-color: ${({ $bgColor }) => $bgColor || "#fff"};
  color: ${({ $containsCircleZero }) =>
    $containsCircleZero ? "#808080" : "#000"};
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  text-align: center;
  font-family: ${({ $isHeader }) =>
    $isHeader ? "'Inter SemiBold', sans-serif" : "'Inter Regular', sans-serif"};
  font-size: 18px;
  box-sizing: border-box;
  min-width: 215px;
  margin-top: -1px;
  cursor: ${({ $isClickable }) => ($isClickable ? "pointer" : "default")};

  &:nth-child(-n + 5) {
    font-family: "Inter SemiBold", sans-serif;
    font-weight: 600;
  }

  &:nth-child(n + 6) {
    font-family: "Inter Regular", sans-serif;
    font-weight: 400;
  }
`;

const isCircleZero = (text) => text.includes("⓪");

const TShapeTable = ({ hardMarks = [], onUpdate }) => {
  const [isModalVisible, setModalVisible] = useState(false);
  const [selectedSkill, setSelectedSkill] = useState(null);
  const [selectedLevel, setSelectedLevel] = useState(0);
  const [isLoading, setIsLoading] = useState(false);

  const toggleModal = useCallback(() => {
    setModalVisible((prev) => !prev);
  }, []);

  const handleItemClick = useCallback(
    (skill) => {
      if (skill) {
        setSelectedSkill(skill);
        setSelectedLevel(skill.mark);
        toggleModal();
      }
    },
    [toggleModal]
  );

  const sortedHardMarks = useMemo(() => {
    const sorted = [...hardMarks]
      .sort(
        (a, b) =>
          b.hard_skills.filter((skill) => skill.mark > 0).length -
          a.hard_skills.filter((skill) => skill.mark > 0).length
      )
      .slice(0, 5);

    while (sorted.length < 5) {
      sorted.push({ role_name: "Пусто", hard_skills: [] });
    }

    const centerIndex = Math.floor(sorted.length / 2);
    const maxSkillsIndex = sorted.findIndex(
      (role) =>
        role.hard_skills.filter((skill) => skill.mark > 0).length ===
        Math.max(
          ...sorted.map(
            (r) => r.hard_skills.filter((skill) => skill.mark > 0).length
          )
        )
    );

    if (maxSkillsIndex !== -1 && maxSkillsIndex !== centerIndex) {
      const [maxSkillRole] = sorted.splice(maxSkillsIndex, 1);
      sorted.splice(centerIndex, 0, maxSkillRole);
    }

    return sorted;
  }, [hardMarks]);

  const maxSkillsCount = useMemo(
    () => Math.max(...sortedHardMarks.map((role) => role.hard_skills.length)),
    [sortedHardMarks]
  );

  return (
    <TableWrapper>
      <Table>
        {sortedHardMarks.map((role, index) => (
          <TableItem
            key={index}
            $bgColor={colorMap[role.role_name]}
            $isHeader={true}
          >
            {role.role_name}
          </TableItem>
        ))}

        {Array.from({ length: maxSkillsCount }).map((_, rowIndex) =>
          sortedHardMarks.map((role, colIndex) => {
            const skill = role.hard_skills[rowIndex];
            const markIcon = skill ? getIconForMark(skill.mark) : "";
            const backgroundColor =
              skill && skill.mark > 0 ? colorMap[role.role_name] : "#FFFFFF";
            const containsCircleZero = skill ? isCircleZero(markIcon) : false;

            return (
              <TableItem
                key={`${rowIndex}-${colIndex}`}
                $bgColor={backgroundColor}
                $containsCircleZero={containsCircleZero}
                onClick={() => handleItemClick(skill)}
                $isClickable={!!skill}
              >
                {markIcon} {skill ? skill.skill_name : ""}
              </TableItem>
            );
          })
        )}
      </Table>

      <ModalOverlay $isVisible={isModalVisible} onClick={toggleModal}>
        <ModalContent onClick={(e) => e.stopPropagation()}>
          <T1House src={T1_House} alt="House" />
          <T1Computer src={T1_Computer} alt="Computer" />
          <CloseButton onClick={toggleModal}>&times;</CloseButton>
          {selectedSkill && (
            <>
              <ModalHeader>Компетенция {selectedSkill.skill_name}</ModalHeader>
              <ModalInputTextHeader>Способы оценки:</ModalInputTextHeader>
              <ModalInputText>{selectedSkill.grade_method}</ModalInputText>
              <ModalInputTextHeader>Способы развития:</ModalInputTextHeader>
              <ModalInputText>{selectedSkill.develop_method}</ModalInputText>
              <ModalInputTextHeader>Индикаторы:</ModalInputTextHeader>
              <>
                {selectedSkill.indicators.map((indicator, index) => (
                  <ModalInputText key={index}>{indicator}</ModalInputText>
                ))}
              </>
            </>
          )}
        </ModalContent>
      </ModalOverlay>
    </TableWrapper>
  );
};

export default TShapeTable;
