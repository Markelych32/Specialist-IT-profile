import React, { useState, useRef, useEffect, useCallback } from "react";
import styled from "styled-components";
import TShapeTable from "./ATShapeTable/ATShapeTable";
import SpecificationCards from "./SpecificationCards/SpecificationCards";
import {
  ErrorMessage,
  SuccessNotificationText,
  SuccessNotification,
  T1Computer,
  T1House,
  Select,
  SaveButton,
  CloseButton,
  ModalInputTextHeader,
  ModalContent,
  ModalHeader,
  ModalOverlay2,
  ModalInput,
  OptionsList,
  OptionItem,
  FailNotification,
  FailNotificationText,
  ModalInputText,
} from "../../../../common/ModalWindow/ModalWindow";
import LegendInfo from "../../../../common/LegendInfo/LegendInfo";
import {
  getHardTable,
  getHardAddingItems,
  getSuitableCompetences,
  addSkill,
  updateAddingSkill,
} from "../../../../../../api/GetPostResponses";
import getIconForMark from "../../../../common/IconsForMarks/IconsForMarks";
import colorMap from "../../../../common/ColorMap/ColorMap";
import ArrowIconUp from "../../../../assets/images/ArrowUp.png";
import ArrowIcon from "../../../../assets/images/ArrowDown.png";
import T1_House from "../../../../assets/images/T1_House.png";
import T1_Computer from "../../../../assets/images/T1_Computer.png";
import LoadingAnim from "../../../../assets/images/LoadingAnim.gif";

const getBackgroundColor = (number) => {
  switch (number) {
    case 1:
      return "#FFE875";
    case 2:
      return "#FF7D7D";
    case 3:
      return "#49B0FA";
    case 4:
      return "#C889FF";
    case 5:
      return "#FE9D5C";
    default:
      return "#FFFFFF";
  }
};

const HardContainer = ({ specificationsData }) => {
  const [showContent, setShowContent] = useState(false);
  const [contentHeight, setContentHeight] = useState(0);
  const [isModalVisible, setModalVisible] = useState(false);
  const [isNotificationVisible, setNotificationVisible] = useState(false);
  const [isNotificationVisibleErr, setNotificationVisibleErr] = useState(false);
  const [hardMarks, setHardMarks] = useState([]);
  const contentRef = useRef(null);
  const [Addingcompetences, setAddingcompetences] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isSaveDisabled, setIsSaveDisabled] = useState(true);
  const [isEditModalVisible, setEditModalVisible] = useState(false);
  const [selectedEditSkill, setSelectedEditSkill] = useState(null);
  const [selectedEditLevel, setSelectedEditLevel] = useState(0);
  const [isEditLoading, setIsEditLoading] = useState(false);
  const [selectedEditSkillName, setSelectedEditSkillName] = useState("");

  const [select2Value, setSelect2Value] = useState("");
  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const [showErrorMessageSave, setShowErrorMessageSave] = useState(false);
  const [inputValue, setInputValue] = useState("");
  const [options, setOptions] = useState([]);
  const [selectedSkill, setSelectedSkill] = useState(null);
  const [isSearching, setIsSearching] = useState(true);

  const toggleEditModal = useCallback(() => {
    setEditModalVisible((prev) => !prev);
  }, []);
  const handleInputChangeValid = (event) => {
    const value = event.target.value;

    const validCharacters = /^[a-zA-Zа-яА-Я0-9\s]*$/;

    if (value.length <= 150 && validCharacters.test(value)) {
      setInputValue(value);
      setShowErrorMessage(false);
      setIsSaveDisabled(false);
    } else {
      setShowErrorMessage(true);
      setIsSaveDisabled(true);
    }

    setSelectedSkill(null);
    setIsSearching(true);
  };
  const handleEditItemClick = useCallback(
    (skill) => {
      if (skill) {
        setSelectedEditSkill(skill);
        setSelectedEditSkillName(skill.name);
        setSelectedEditLevel(skill.mark);
        toggleEditModal();
      }
    },
    [toggleEditModal]
  );

  const handleEditLevelChange = useCallback((event) => {
    setSelectedEditLevel(Number(event.target.value));
  }, []);

  const handleEditSave = useCallback(async () => {
    setIsEditLoading(true);

    try {
      const competence = Addingcompetences.find(
        (comp) =>
          comp.name.toLowerCase() === selectedEditSkillName.toLowerCase()
      );

      if (!competence) {
        console.error("Competence not found");
        setIsEditLoading(false);
        return;
      }

      const competenceId = competence.id;
      await updateAddingSkill(1, competenceId, selectedEditLevel);

      toggleEditModal();
      fetchHardAdding();
    } catch (error) {
      console.error("Error saving skill:", error);
    } finally {
      setIsEditLoading(false);
    }
  }, [
    Addingcompetences,
    selectedEditSkillName,
    selectedEditLevel,
    toggleEditModal,
  ]);

  const toggleContent = useCallback(() => {
    setShowContent((prevState) => !prevState);
  }, []);

  const handleInputChange = useCallback((event) => {
    setInputValue(event.target.value);
    setIsSaveDisabled(true);
    setSelectedSkill(null);
    setIsSearching(true);
    setShowErrorMessageSave(false);
  }, []);

  const handleOptionSelect = useCallback((option) => {
    setInputValue(option.skill_name);
    setSelect2Value(option.role_name);
    setSelectedSkill(option);
    setOptions([]);
    setIsSaveDisabled(false);
    setIsSearching(false);
    setShowErrorMessageSave(false);
  }, []);

  const toggleModal = useCallback(() => {
    setModalVisible((prev) => !prev);
    if (isModalVisible) {
      setInputValue("");
      setSelect2Value("");
      setSelectedSkill(null);
      setShowErrorMessageSave(false);
      setIsSaveDisabled(true);
      setIsSearching(true);
    }
  }, [isModalVisible]);

  const colorMapRole = {
    "Системный аналитик": "1",
    "Backend разработчик": "2",
    "Frontend разработчик": "3",
    "Ручной тестировщик": "4",
    "Прикладной администратор": "5",
    Другое: "6",
  };

  const handleSave = useCallback(async () => {
    if (!inputValue) {
      setShowErrorMessageSave(true);
      return;
    }

    setShowErrorMessageSave(false);
    const isCompetenceExists = Addingcompetences.some(
      (competence) => competence.name.toLowerCase() === inputValue.toLowerCase()
    );

    if (isCompetenceExists) {
      setNotificationVisibleErr(true);
      setInputValue("");
      setSelect2Value("");
      setSelectedSkill(null);
      setTimeout(() => {
        setNotificationVisibleErr(false);
      }, 4000);
      return;
    }

    try {
      setNotificationVisible(true);
      const skillId = colorMapRole[select2Value] || "6";

      await addSkill(1, inputValue, skillId);
      setInputValue("");
      setSelect2Value("");
      setSelectedSkill(null);
      setTimeout(() => {
        setNotificationVisible(false);
      }, 4000);
    } catch (error) {
      console.error("Ошибка при сохранении навыка:", error);
      setTimeout(() => {
        setNotificationVisible(false);
      }, 4000);
    } finally {
      await fetchHardAdding();
      toggleModal();
    }
  }, [
    inputValue,
    Addingcompetences,
    select2Value,
    fetchHardAdding,
    toggleModal,
  ]);

  useEffect(() => {
    if (inputValue && isSearching) {
      const delayDebounceFn = setTimeout(async () => {
        const result = await getSuitableCompetences(inputValue);

        const matchingOption = result.find(
          (option) =>
            option.skill_name.toLowerCase() === inputValue.toLowerCase()
        );

        if (matchingOption) {
          setInputValue(matchingOption.skill_name);
          setSelect2Value(matchingOption.role_name);
          setSelectedSkill(matchingOption);
          setOptions([]);
          setIsSaveDisabled(false);
          setIsSearching(false);
        } else {
          setOptions(result);
        }
      }, 200);

      return () => clearTimeout(delayDebounceFn);
    }
  }, [inputValue, isSearching]);

  const handleBlur = useCallback(() => {
    setTimeout(() => {
      const matchingOption = options.find(
        (option) => option.skill_name.toLowerCase() === inputValue.toLowerCase()
      );

      if (matchingOption) {
        setInputValue(matchingOption.skill_name);
        setSelect2Value(matchingOption.role_name);
        setSelectedSkill(matchingOption);
        setIsSaveDisabled(false);
      }

      setOptions([]);
    }, 200);
  }, [options, inputValue]);

  useEffect(() => {
    if (isModalVisible) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isModalVisible]);

  useEffect(() => {
    if (contentRef.current) {
      setContentHeight(contentRef.current.scrollHeight);
    }
  }, [Addingcompetences, showContent]);

  const fetchHardMarks = useCallback(async () => {
    try {
      const data = await getHardTable(1);
      setHardMarks(data.hard_marks);
    } catch (error) {
      console.error("Ошибка при получении навыков:", error);
      setHardMarks([]);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchHardMarks();
  }, []);

  const fetchHardAdding = useCallback(async () => {
    try {
      const data = await getHardAddingItems(1);
      setAddingcompetences(data.add_competences);
    } catch (error) {
      console.error("Ошибка при получении навыков:", error);
      setAddingcompetences([]);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchHardAdding();
  }, []);

  return (
    <HardBlock>
      <SpecificationCards specificationsData={specificationsData} />
      <Header onClick={!loading ? toggleContent : undefined}>
        <HardListText>
          Карта хардовых компетенций с уровнем прогресса
        </HardListText>
        <ArrowImage
          src={loading ? LoadingAnim : showContent ? ArrowIconUp : ArrowIcon}
          alt="Toggle"
        />
      </Header>
      {!loading && (
        <Content $show={showContent} height={contentHeight} ref={contentRef}>
          <TShapeTable hardMarks={hardMarks} onUpdate={fetchHardMarks} />
          <Line />
          <AddingBlock>
            <AddingHeader>
              Дополнительные компетенции
              <PlusButton onClick={toggleModal}>+</PlusButton>
            </AddingHeader>
            <ContentWrapper>
              {Addingcompetences.map((competence) => (
                <AddingItem
                  key={competence.id}
                  number={competence.mark}
                  style={{
                    backgroundColor:
                      colorMap[competence.role_name] || colorMap["Пусто"],
                    cursor: "pointer",
                  }}
                  onClick={() => handleEditItemClick(competence)}
                >
                  {getIconForMark(competence.mark)} {competence.name}
                </AddingItem>
              ))}
            </ContentWrapper>
          </AddingBlock>

          <Line />
          <LegendInfo showCountLegend={4} />
        </Content>
      )}

      <ModalOverlay2 $isVisible={isEditModalVisible} onClick={toggleEditModal}>
        <ModalContent onClick={(e) => e.stopPropagation()}>
          <T1House src={T1_House} alt="House" />
          <T1Computer src={T1_Computer} alt="Computer" />
          <CloseButton onClick={toggleEditModal}>&times;</CloseButton>
          {selectedEditSkill && (
            <>
              <ModalHeader>Компетенция</ModalHeader>
              <ModalInputTextHeader>
                Изменить уровень компетенции:
              </ModalInputTextHeader>
              <Select
                value={selectedEditLevel}
                onChange={handleEditLevelChange}
              >
                {[0, 1, 2, 3].map((level) => (
                  <option key={level} value={level}>
                    {level}
                  </option>
                ))}
              </Select>
              <SaveButton onClick={handleEditSave}>
                {isEditLoading ? "Сохранение..." : "Сохранить"}
              </SaveButton>
            </>
          )}
        </ModalContent>
      </ModalOverlay2>

      <ModalOverlay2 $isVisible={isModalVisible} onClick={toggleModal}>
        <ModalContent onClick={(e) => e.stopPropagation()}>
          <T1House src={T1_House} alt="House" />
          <T1Computer src={T1_Computer} alt="Computer" />
          <CloseButton onClick={toggleModal}>&times;</CloseButton>
          <ModalHeader>Добавление новой дополнительной компетенции</ModalHeader>

          <ModalInput
            type="text"
            value={inputValue}
            onChange={handleInputChangeValid}
            onBlur={handleBlur}
            onFocus={() => setShowErrorMessage(false)}
            placeholder="Введите название компетенции"
          />

          {showErrorMessage && (
            <p style={{ color: "red", fontSize: "15px", marginTop: "5px" }}>
              Ввод допускает только буквы и цифры (макс. 150 символов).
            </p>
          )}

          {!showErrorMessage && options.length > 0 && (
            <OptionsList>
              {options.map((option) => (
                <OptionItem
                  key={option.skill_id}
                  onClick={() => handleOptionSelect(option)}
                >
                  {option.skill_name}
                </OptionItem>
              ))}
            </OptionsList>
          )}

          <Select
            value={selectedSkill ? select2Value : "Другое"}
            onChange={(e) => setSelect2Value(e.target.value)}
            disabled={true}
          >
            <option value="" disabled>
              Выберите роль компетенции
            </option>
            <option value="Системный аналитик">Системный аналитик</option>
            <option value="Backend разработчик">Backend разработчик</option>
            <option value="Frontend разработчик">Frontend разработчик</option>
            <option value="Ручной тестировщик">Ручной тестировщик</option>
            <option value="Прикладной администратор">
              Прикладной администратор
            </option>
            <option value="Другое" disabled>
              Другое
            </option>
          </Select>

          <SaveButton onClick={handleSave}>Сохранить</SaveButton>

          {showErrorMessageSave && (
            <ErrorMessage>Заполните все данные</ErrorMessage>
          )}
        </ModalContent>
      </ModalOverlay2>

      <SuccessNotification className={isNotificationVisible ? "show" : ""}>
        <h3>Успех!</h3>
        <SuccessNotificationText>Данные сохранены!</SuccessNotificationText>
      </SuccessNotification>
      <FailNotification className={isNotificationVisibleErr ? "show" : ""}>
        <h3>Что-то пошло не так!</h3>
        <FailNotificationText>
          Компетенция была добавлена ранее!
        </FailNotificationText>
      </FailNotification>
    </HardBlock>
  );
};

const HardBlock = styled.div`
  background-color: #eef1f6;
  border-radius: 12px;
  padding-top: 10px;
  border: 6px solid white;
  display: flex;
  flex-direction: column;
  position: relative;
  @media (min-width: 1500px) {
    width: 100%;
  }
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ffffff;
  border-radius: 12px;
  gap: 10px;
  margin: 30px 10px 10px;
  cursor: pointer;
  font-family: "Inter", sans-serif;
  font-weight: 600;
  font-size: 24px;
  color: #333;
  box-sizing: border-box;
`;

const ContentWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
  margin-top: 20px;
`;

const PlusButton = styled.span`
  font-size: 36px;
  font-weight: 500;
  cursor: pointer;
`;

const AddingHeader = styled.div`
  color: #000000;
  font-family: "Inter", sans-serif;
  font-weight: 600;
  margin-bottom: 10px;
  margin-left: 10px;
  margin-right: 10px;
  font-size: 18px;
  text-align: left;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Content = styled.div`
  overflow: hidden;
  max-height: ${(props) => (props.$show ? `${props.height}px` : "0")};
  transition: max-height 0.7s ease-in-out;
  margin-top: 10px;
  box-sizing: border-box;
  margin: 0px 10px 0;
`;

const AddingBlock = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
  width: 100%;
  box-sizing: border-box;
`;

const ArrowImage = styled.img`
  width: 72px;
  height: 72px;
`;

const HardListText = styled.span`
  margin-left: 10px;
`;

const Line = styled.div`
  text-align: center;
  width: 97%;
  height: 2px;
  background-color: #bdbac0;
  margin: 20px auto;
`;

const AddingItem = styled.div`
  background-color: ${(props) => getBackgroundColor(props.number)};
  border-radius: 12px;
  padding: 20px;
  color: #000;
  font-size: 18px;
  font-family: "Inter", sans-serif;
  font-weight: 200;
  margin: 5px;
`;

export default HardContainer;
