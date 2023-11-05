import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { updateUserProfile } from '../../../services/UserService';

interface ProfileEditModalProps {
  userProfile: UserProfileData;
  handleClose: () => void;
}

interface UserProfileData {
  username: string;
  organization: string;
  location: string;
  description: string;
  linkedin: string;
  github: string;
  resume: string;
  profilePicture: string;
}

const ProfileEditModal: React.FC<ProfileEditModalProps> = ({
  userProfile,
  handleClose,
}) => {
  const [formData, setFormData] = useState<UserProfileData>(userProfile);
  const [errorMsg, setErrorMsg] = useState('');

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleSubmit = async (event: any) => {

    event.preventDefault();

    const data = { username: formData.username, organization: formData.organization, 
                    location:formData.location, description: formData.description, linkedin: formData.linkedin,
                    github: formData.github, resume: formData.resume, profilePicture: formData.profilePicture
    }

    const response = await updateUserProfile(data);

    if (response?.status === 200) {
      handleClose();
    }
    setErrorMsg('Something went wrong!');
    
  };

  return (
    <Modal show={true} onHide={handleClose}>

      <Modal.Header closeButton>
        <Modal.Title>Edit Profile</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="organization">
            <Form.Label>Organization</Form.Label>
            <Form.Control
              type="text"
              name="organization"
              placeholder='Enter Your Organization'
              value={formData.organization}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="location">
            <Form.Label>Location</Form.Label>
            <Form.Control
              type="text"
              name="location"
              placeholder='Enter Your Location'
              value={formData.location}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="description">
            <Form.Label>Description</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              name="description"
              placeholder='Enter Profile Description'
              value={formData.description}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="linkedin">
            <Form.Label>LinkedIn</Form.Label>
            <Form.Control
              type="text"
              name="linkedin"
              placeholder='Enter Your LinkedIn Profile Link'
              value={formData.linkedin}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="github">
            <Form.Label>GitHub</Form.Label>
            <Form.Control
              type="text"
              name="github"
              placeholder='Enter Your Github Profile Link'
              value={formData.github}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="resume">
            <Form.Label>Resume</Form.Label>
            <Form.Control
              type="text"
              placeholder='Enter Your Resume Link'
              name="resume"
              value={formData.resume}
              onChange={handleChange}
            />
          </Form.Group>
          <Form.Group controlId="profilePicture">
            <Form.Label>Profile Picture</Form.Label>
            <Form.Control
              type="text"
              placeholder='Enter Profile Picture Link'
              name="profilePicture"
              value={formData.profilePicture}
              onChange={handleChange}
            />
          </Form.Group>
          <Button variant="primary" type="submit" style={{ marginTop: '2em' }}>
            Save Changes
          </Button>
          {errorMsg !== "" &&
            <Form.Group className="mt-1" controlId="errorMsg">
              <Form.Label color="red">{errorMsg}</Form.Label>
            </Form.Group>
          }
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ProfileEditModal;
