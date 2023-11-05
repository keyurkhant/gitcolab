import React, { useEffect, useState } from 'react';
import { Button, Card, Image, Row, Col } from 'react-bootstrap';
import { FaLinkedin, FaGithub, FaCloud } from 'react-icons/fa';
import ProfileEditModal from './ProfileEditModal/ProfileEditModal';
import { useSelector } from 'react-redux';
import { selectUser } from '../../redux/userSlice';
import { getUserData } from '../../services/UserService';


interface UserProfileProps {
  username: string;
  organization: string;
  location: string;
  description: string;
  linkedin: string;
  github: string;
  resume: string;
  profilePicture: string;
}

// eslint-disable-next-line no-empty-pattern
const UserProfile: React.FC<UserProfileProps> = ({
}) => {
  const [showEditModal, setShowEditModal] = useState(false);
  const [userData, setUserData] = useState({} as any);
  const userDataStore = useSelector(selectUser);

  const handleEditProfile = () => {
    setShowEditModal(true);
  };

  const handleModalClose = () => {
    setShowEditModal(false);
    window.location.reload();
  };

  useEffect(() => {
    async function fetchUserData() {
      let response = await getUserData(userDataStore.username, userDataStore.token);
      if (response?.status === 200) {
        setUserData(response?.data?.body);
      }
    }
    fetchUserData()
  }, [userDataStore.token, userDataStore.username])

  return (
    <Card className="user-profile" style={{ width: '100%', marginRight: '10em', borderRadius: '0.4em', borderColor: 'black', borderWidth: '0.2em' }}>
      <Row>
        <Col md={4}> <Image src={userData.profilePicture ? userData.profilePicture : 'https://pasrc.princeton.edu/sites/g/files/toruqf431/files/styles/freeform_750w/public/2021-03/blank-profile-picture_0.jpg?itok=iSBmDxc8'} alt="Profile Picture"
          style={{ maxWidth: '100%', width: "100%", height: "30em" }} /></Col>
        <Col md={8}>

          <Card.Body>

            <Card.Title style={{ "paddingBottom": 10 }}>
              {userData.firstName} {userData.lastName}
            </Card.Title>
            <Card.Text>Organization: {userData.organization}</Card.Text>
            <Card.Text>Location: {userData.location}</Card.Text>
            <Card.Text>Description: {userData.description}</Card.Text>
            <Card.Text>
              LinkedIn:{' '}
              <a href={userData.linkedin} target="_blank" rel="noopener noreferrer">
                <FaLinkedin />
              </a>
            </Card.Text>
            <Card.Text>
              GitHub:{' '}
              <a href={userData.github} target="_blank" rel="noopener noreferrer">
                <FaGithub />
              </a>
            </Card.Text>
            <Card.Text>
              Resume:{' '}
              <a href={userData.resume} target="_blank" rel="noopener noreferrer">
                <FaCloud />
              </a>
            </Card.Text>
            <Button variant="primary" onClick={handleEditProfile} style={{ marginTop: '0.5em' }}>
              Edit Profile
            </Button>

          </Card.Body>
          {showEditModal && (
            <ProfileEditModal
              userProfile={{
                ...userData
              }}
              handleClose={handleModalClose}
            />
          )}</Col>
      </Row>
    </Card>
  );
};

export default UserProfile;
